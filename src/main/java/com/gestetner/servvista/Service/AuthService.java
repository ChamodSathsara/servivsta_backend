package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.auth.LoginRequest;
import com.gestetner.servvista.Dto.auth.LoginResponse;
import com.gestetner.servvista.Dto.auth.RefreshTokenRequest;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

    private static final String INVALID_CREDENTIALS = "Invalid email or password";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public LoginResponse login(LoginRequest request, String ipAddress) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException(INVALID_CREDENTIALS));

        if (!Boolean.TRUE.equals(user.getIsActive())
                || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException(INVALID_CREDENTIALS);
        }

        RefreshTokenService.IssuedRefreshToken refreshToken =
                refreshTokenService.issue(user, request.deviceLabel(), ipAddress);
        return createResponse(user, refreshToken);
    }

    @Transactional
    public LoginResponse refresh(RefreshTokenRequest request, String ipAddress) {
        RefreshTokenService.RotatedRefreshToken rotatedToken =
                refreshTokenService.rotate(request.refreshToken(), ipAddress);
        return createResponse(rotatedToken.user(), rotatedToken.refreshToken());
    }

    private LoginResponse createResponse(
            User user,
            RefreshTokenService.IssuedRefreshToken refreshToken) {
        JwtService.GeneratedToken accessToken = jwtService.generateAccessToken(user);
        return new LoginResponse(
                accessToken.value(),
                "Bearer",
                accessToken.expiresAt(),
                refreshToken.value(),
                refreshToken.expiresAt(),
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole());
    }
}
