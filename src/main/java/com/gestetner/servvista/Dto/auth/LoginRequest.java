package com.gestetner.servvista.Dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email or technician code is required")
        @Size(max = 150, message = "Login identifier must not exceed 150 characters")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @Size(max = 100, message = "Device label must not exceed 100 characters")
        String deviceLabel
) {
}
