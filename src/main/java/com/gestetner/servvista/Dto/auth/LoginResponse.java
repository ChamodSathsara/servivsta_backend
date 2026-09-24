package com.gestetner.servvista.Dto.auth;

import com.gestetner.servvista.Models.Enums.Identity.Role;

import java.time.Instant;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Instant expiresAt,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        Long userId,
        String userName,
        String email,
        Role role
) {
}
