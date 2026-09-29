package com.gestetner.servvista.Dto.portal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PortalOtpVerificationRequest(
        @NotNull @Positive Long portalAccountId,
        @NotNull @Positive Long machineId,
        @NotBlank @Pattern(regexp = "\\d{6}") String otpCode
) {
}
