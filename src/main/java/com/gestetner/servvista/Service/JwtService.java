package com.gestetner.servvista.Service;

import com.gestetner.servvista.Models.entity.identity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final String issuer;
    private final long accessTokenExpirationSeconds;
    private final Clock clock;

    @Autowired
    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.access-token-expiration-seconds}") long accessTokenExpirationSeconds) {
        this(jwtEncoder, issuer, accessTokenExpirationSeconds, Clock.systemUTC());
    }

    JwtService(JwtEncoder jwtEncoder, String issuer, long accessTokenExpirationSeconds, Clock clock) {
        if (accessTokenExpirationSeconds <= 0) {
            throw new IllegalArgumentException("JWT access token expiration must be positive");
        }
        this.jwtEncoder = jwtEncoder;
        this.issuer = issuer;
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
        this.clock = clock;
    }

    public GeneratedToken generateAccessToken(User user) {
        Instant issuedAt = clock.instant();
        Instant expiresAt = issuedAt.plusSeconds(accessTokenExpirationSeconds);
        String authority = "ROLE_" + user.getRole().name();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString())
                .subject(user.getEmail())
                .claim("userId", user.getUserId())
                .claim("userName", user.getUserName())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .claim("roles", List.of(authority))
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new GeneratedToken(token, expiresAt);
    }

    public record GeneratedToken(String value, Instant expiresAt) {
    }
}
