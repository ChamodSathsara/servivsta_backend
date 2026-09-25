package com.gestetner.servvista.Dto.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RepRequest(
        @NotBlank @Size(max = 20) String repCode,
        @NotBlank @Size(max = 150) String repName,
        @Size(max = 20) String repMobileNumber,
        Boolean isActive,
        @Positive Long createdBy
) {}
