package com.gestetner.servvista.Dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @Size(max = 100, message = "Device label must not exceed 100 characters")
        String deviceLabel
) {
}
