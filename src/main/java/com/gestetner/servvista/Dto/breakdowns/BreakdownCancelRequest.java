package com.gestetner.servvista.Dto.breakdowns;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BreakdownCancelRequest(
        @NotNull @Positive Long cancelledBy,
        @NotBlank @Size(max = 255) String cancelReason
) {
}
