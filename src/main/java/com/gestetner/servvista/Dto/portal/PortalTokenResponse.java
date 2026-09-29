package com.gestetner.servvista.Dto.portal;

import java.time.Instant;

public record PortalTokenResponse(
        String accessToken,
        String tokenType,
        Instant expiresAt,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        Long portalAccountId,
        Long machineId,
        String role
) {
}
