package com.gestetner.servvista.Dto.breakdowns;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BreakdownFeedbackRequest(
        @NotNull @Min(1) @Max(5) Integer rating,
        String comment,
        @NotNull @Positive Long submittedByPortalAccountId
) {
}
