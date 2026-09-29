package com.gestetner.servvista.Dto.portal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PortalRefreshRequest(
        @NotBlank String refreshToken,
        @NotNull @Positive Long machineId
) {
}
