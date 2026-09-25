package com.gestetner.servvista.Dto.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DealerRequest(
        @NotBlank @Size(max = 150)
        String dealerName,

        @Size(max = 255)
        String dealerAddress,

        @Size(max = 30)
        String dealerContactNumber,

        Boolean isActive,

        @Positive
        Long createdBy
) {}
