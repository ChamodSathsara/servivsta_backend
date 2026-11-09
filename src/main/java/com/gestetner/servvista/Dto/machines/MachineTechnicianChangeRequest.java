package com.gestetner.servvista.Dto.machines;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record MachineTechnicianChangeRequest(
        @Positive Long mainTechnicianId,
        @Positive Long serviceTechnicianId,
        @NotNull LocalDate effectiveDate,
        @NotNull @Positive Long performedBy,
        String reason
) {}
