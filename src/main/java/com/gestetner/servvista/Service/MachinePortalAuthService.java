package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.portal.*;
import com.gestetner.servvista.Models.entity.customerportal.*;
import com.gestetner.servvista.Models.entity.customers.SiteContact;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Repositories.customerportal.*;
import com.gestetner.servvista.Repositories.customers.SiteContactRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.*;
import java.util.Base64;
import java.util.HexFormat;

@Service
@Transactional
public class MachinePortalAuthService {

    private static final String OTP_PURPOSE = "MACHINE_LOGIN";
    private static final String INVALID_OTP = "Invalid or expired OTP";
    private final MachineRepository machines;
    private final SiteContactRepository contacts;
    private final CustomerPortalAccountRepository accounts;
    private final PortalAccountMachineRepository grants;
    private final LoginOtpRepository otps;
    private final PortalRefreshTokenRepository refreshTokens;
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SecureRandom random = new SecureRandom();
    private final long refreshExpirationSeconds;

    public MachinePortalAuthService(
            MachineRepository machines,
            SiteContactRepository contacts,
            CustomerPortalAccountRepository accounts,
            PortalAccountMachineRepository grants,
            LoginOtpRepository otps,
            PortalRefreshTokenRepository refreshTokens,
            UserRepository users,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            @Value("${security.jwt.refresh-token-expiration-seconds}") long refreshExpirationSeconds) {
        this.machines = machines;
        this.contacts = contacts;
        this.accounts = accounts;
        this.grants = grants;
        this.otps = otps;
        this.refreshTokens = refreshTokens;
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshExpirationSeconds = refreshExpirationSeconds;
    }

    public MachinePortalRegistrationResponse register(
            Long machineId,
            MachinePortalRegistrationRequest request,
            String ipAddress) {
        Machine machine = machines.findById(machineId)
                .orElseThrow(() -> new EntityNotFoundException("Machine " + machineId + " was not found"));
        SiteContact contact = contacts.findById(request.siteContactId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Site contact " + request.siteContactId() + " was not found"));
        if (!contact.getCustomerSiteId().equals(machine.getCurrentCustomerSiteId())) {
            throw new IllegalArgumentException("Site contact does not belong to the machine's current site");
        }
        if (!users.existsById(request.grantedBy())) {
            throw new EntityNotFoundException("User " + request.grantedBy() + " was not found");
        }

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        CustomerPortalAccount account = accounts.findBySiteContactId(contact.getSiteContactId())
                .orElseGet(() -> {
                    CustomerPortalAccount created = new CustomerPortalAccount();
                    created.setSiteContactId(contact.getSiteContactId());
                    created.setIsActive(true);
                    created.setCreatedAt(now);
                    return accounts.saveAndFlush(created);
                });
        if (!Boolean.TRUE.equals(account.isActive())) {
            account.setIsActive(true);
            accounts.saveAndFlush(account);
        }

        PortalAccountMachine grant = grants
                .findByPortalAccountIdAndMachineIdAndIsActiveTrue(account.getPortalAccountId(), machineId)
                .orElseGet(() -> {
                    PortalAccountMachine created = new PortalAccountMachine();
                    created.setPortalAccountId(account.getPortalAccountId());
                    created.setMachineId(machineId);
                    created.setGrantedAt(now);
                    created.setGrantedBy(request.grantedBy());
                    created.setIsActive(true);
                    return grants.saveAndFlush(created);
                });

        otps.findFirstByPortalAccountIdAndPurposeAndConsumedAtIsNullOrderByCreatedAtDesc(
                account.getPortalAccountId(), OTP_PURPOSE).ifPresent(old -> {
            old.setConsumedAt(now);
            otps.save(old);
        });
        String rawOtp = String.format("%06d", random.nextInt(1_000_000));
        LoginOtp otp = new LoginOtp();
        otp.setPortalAccountId(account.getPortalAccountId());
        otp.setOtpCodeHash(passwordEncoder.encode(rawOtp));
        otp.setPurpose(OTP_PURPOSE);
        otp.setExpiresAt(now.plusMinutes(5));
        otp.setAttemptCount(0);
        otp.setCreatedAt(now);
        otp.setRequestedIp(normalizeIp(ipAddress));
        otp = otps.saveAndFlush(otp);

        return new MachinePortalRegistrationResponse(
                account.getPortalAccountId(), grant.getPortalAccountMachineId(), machineId,
                machine.getMachineReferenceNumber(), contact.getEmail(), otp.getLoginOtpId(),
                otp.getExpiresAt(), rawOtp);
    }

    public PortalTokenResponse verifyOtp(PortalOtpVerificationRequest request) {
        CustomerPortalAccount account = requireAccount(request.portalAccountId());
        requireGrant(request.portalAccountId(), request.machineId());
        LoginOtp otp = otps.findFirstByPortalAccountIdAndPurposeAndConsumedAtIsNullOrderByCreatedAtDesc(
                        request.portalAccountId(), OTP_PURPOSE)
                .orElseThrow(() -> new BadCredentialsException(INVALID_OTP));
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        if (!otp.getExpiresAt().isAfter(now) || otp.getAttemptCount() >= 5
                || !passwordEncoder.matches(request.otpCode(), otp.getOtpCodeHash())) {
            otp.setAttemptCount(otp.getAttemptCount() + 1);
            otps.saveAndFlush(otp);
            throw new BadCredentialsException(INVALID_OTP);
        }
        otp.setConsumedAt(now);
        otps.saveAndFlush(otp);
        return tokens(account, request.machineId());
    }

    public PortalTokenResponse refresh(PortalRefreshRequest request) {
        PortalRefreshToken current = refreshTokens.findByTokenHash(hash(request.refreshToken()))
                .orElseThrow(() -> new BadCredentialsException("Invalid or expired portal refresh token"));
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        if (current.getRevokedAt() != null || !current.getExpiresAt().isAfter(now)) {
            throw new BadCredentialsException("Invalid or expired portal refresh token");
        }
        CustomerPortalAccount account = requireAccount(current.getPortalAccountId());
        requireGrant(account.getPortalAccountId(), request.machineId());
        current.setRevokedAt(now);
        refreshTokens.saveAndFlush(current);
        return tokens(account, request.machineId());
    }

    private PortalTokenResponse tokens(CustomerPortalAccount account, Long machineId) {
        SiteContact contact = contacts.findById(account.getSiteContactId())
                .orElseThrow(() -> new EntityNotFoundException("Portal site contact was not found"));
        JwtService.GeneratedToken access = jwtService.generatePortalMachineAccessToken(
                account.getPortalAccountId(), machineId, contact.getEmail());
        Instant issuedAt = Instant.now();
        Instant refreshExpires = issuedAt.plusSeconds(refreshExpirationSeconds);
        String rawRefresh = randomToken();
        PortalRefreshToken refresh = new PortalRefreshToken();
        refresh.setPortalAccountId(account.getPortalAccountId());
        refresh.setTokenHash(hash(rawRefresh));
        refresh.setIssuedAt(LocalDateTime.ofInstant(issuedAt, ZoneOffset.UTC));
        refresh.setExpiresAt(LocalDateTime.ofInstant(refreshExpires, ZoneOffset.UTC));
        refreshTokens.saveAndFlush(refresh);
        return new PortalTokenResponse(access.value(), "Bearer", access.expiresAt(), rawRefresh,
                refreshExpires, account.getPortalAccountId(), machineId, "MACHINE");
    }

    private CustomerPortalAccount requireAccount(Long id) {
        CustomerPortalAccount account = accounts.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Invalid portal account"));
        if (!Boolean.TRUE.equals(account.isActive())) {
            throw new BadCredentialsException("Portal account is inactive");
        }
        return account;
    }

    private void requireGrant(Long accountId, Long machineId) {
        grants.findByPortalAccountIdAndMachineIdAndIsActiveTrue(accountId, machineId)
                .orElseThrow(() -> new BadCredentialsException("Portal account has no access to this machine"));
    }

    private String randomToken() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new IllegalStateException("Could not hash portal token", exception);
        }
    }

    private String normalizeIp(String ip) {
        return ip == null ? null : ip.substring(0, Math.min(ip.length(), 45));
    }
}
