package com.gestetner.servvista.Dto.technician;

import com.gestetner.servvista.Dto.estimates.EstimateLineRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TechnicianEstimateRequest(
        @NotNull @Positive Long machineId,
        @Positive Long serviceScheduleId,
        @Positive Long breakdownId,
        @Size(max = 255) String statusReason,
        @NotEmpty List<@Valid EstimateLineRequest> lines) {
}
