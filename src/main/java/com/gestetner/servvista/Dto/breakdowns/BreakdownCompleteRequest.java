package com.gestetner.servvista.Dto.breakdowns;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BreakdownCompleteRequest(
        @NotNull @Positive Long actualSolutionTypeId,
        @NotBlank String solutionNote
) {
}
