package com.gestetner.servvista.Dto.machines;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SiteContactData(
        @NotBlank @Size(max = 150) String contactName,
        @Size(max = 20) String mobileNumber,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(max = 80) String designation,
        Boolean isPrimary,
        Boolean isActive
) {
}
