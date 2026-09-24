package com.gestetner.servvista.Service;

import com.gestetner.servvista.Models.entity.identity.RefreshToken;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Repositories.identity.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class RefreshTokenService {

    private static final String INVALID_REFRESH_TOKEN = "Invalid or expired refresh token";
    private static final int TOKEN_BYTES = 32;

    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpirationSeconds;
    private final SecureRandom secureRandom;
    private final Clock clock;

    @Autowired
    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${security.jwt.refresh-token-expiration-seconds}") long refreshTokenExpirationSeconds) {
        this(refreshTokenRepository, refreshTokenExpirationSeconds, new SecureRandom(), Clock.systemUTC());
    }

    RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            long refreshTokenExpirationSeconds,
            SecureRandom secureRandom,
            Clock clock) {
        if (refreshTokenExpirationSeconds <= 0) {
            throw new IllegalArgumentException("Refresh token expiration must be positive");
        }
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpirationSeconds = refreshTokenExpirationSeconds;
        this.secureRandom = secureRandom;
        this.clock = clock;
    }

    public IssuedRefreshToken issue(User user, String deviceLabel, String ipAddress) {
        Instant issuedAt = clock.instant();
        Instant expiresAt = issuedAt.plusSeconds(refreshTokenExpirationSeconds);
        String rawToken = generateRawToken();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getUserId());
        refreshToken.setTokenHash(hash(rawToken));
        refreshToken.setDeviceLabel(normalizeDeviceLabel(deviceLabel));
        refreshToken.setIssuedAt(LocalDateTime.ofInstant(issuedAt, ZoneOffset.UTC));
        refreshToken.setExpiresAt(LocalDateTime.ofInstant(expiresAt, ZoneOffset.UTC));
        refreshToken.setIpAddress(normalizeIpAddress(ipAddress));
        refreshTokenRepository.save(refreshToken);

        return new IssuedRefreshToken(rawToken, expiresAt);
    }

    public RotatedRefreshToken rotate(String rawToken, String ipAddress) {
        RefreshToken currentToken = refreshTokenRepository.findByTokenHashForUpdate(hash(rawToken))
                .orElseThrow(() -> new BadCredentialsException(INVALID_REFRESH_TOKEN));

        Instant now = clock.instant();
        Instant expiresAt = currentToken.getExpiresAt().toInstant(ZoneOffset.UTC);
        User user = currentToken.getUser();

        if (currentToken.getRevokedAt() != null
                || !expiresAt.isAfter(now)
                || user == null
                || !Boolean.TRUE.equals(user.getIsActive())) {
            throw new BadCredentialsException(INVALID_REFRESH_TOKEN);
        }

        currentToken.setRevokedAt(LocalDateTime.ofInstant(now, ZoneOffset.UTC));
        refreshTokenRepository.save(currentToken);
        IssuedRefreshToken replacement = issue(user, currentToken.getDeviceLabel(), ipAddress);
        return new RotatedRefreshToken(user, replacement);
    }

    private String generateRawToken() {
        byte[] tokenBytes = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private String hash(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            throw new BadCredentialsException(INVALID_REFRESH_TOKEN);
        }
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }

    private String normalizeDeviceLabel(String deviceLabel) {
        if (deviceLabel == null || deviceLabel.isBlank()) {
            return null;
        }
        return deviceLabel.trim();
    }

    private String normalizeIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.isBlank()) {
            return null;
        }
        String normalized = ipAddress.trim();
        return normalized.length() <= 45 ? normalized : normalized.substring(0, 45);
    }

    public record IssuedRefreshToken(String value, Instant expiresAt) {
    }

    public record RotatedRefreshToken(User user, IssuedRefreshToken refreshToken) {
    }
}
