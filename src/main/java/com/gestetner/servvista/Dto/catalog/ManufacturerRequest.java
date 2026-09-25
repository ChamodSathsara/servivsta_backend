package com.gestetner.servvista.Dto.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ManufacturerRequest(
        @NotBlank @Size(max = 150) String manufacturerName,
        Boolean isActive,
        @Positive Long createdBy
) {}
