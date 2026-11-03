package com.gestetner.servvista.Dto.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceVisitCompleteRequest(
        @NotNull @Positive Long solutionTypeId,
        @NotBlank String solutionNote
) {
}
