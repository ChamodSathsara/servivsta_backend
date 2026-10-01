package com.gestetner.servvista.Dto.breakdowns;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BreakdownCreateRequest(
        @NotNull @Valid BreakdownRequest breakdown,
        @NotNull @Valid BreakdownAssignmentRequest technicianAssignment
) {
}
