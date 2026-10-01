package com.gestetner.servvista.Dto.breakdowns;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BreakdownTechnicianChangeRequest(
        @NotNull @Positive Long technicianId,
        @NotNull @Positive Long assignedBy,
        @Size(max = 255) String reason
) {
}
