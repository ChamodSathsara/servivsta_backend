package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record EstimateRequest(@NotNull @Positive Long machineId,
                              @Positive Long serviceScheduleId,
                              @Positive Long breakdownId,
                              @NotNull @Positive Long technicianId,
                              EstimateStatus status,
                              @Positive Long approvedBy,
                              @NotNull @Positive Long performedBy,
                              @Size(max=255) String statusReason,
                              @NotEmpty List<@Valid EstimateLineRequest> lines,
                              @Valid EstimateAcceptanceData acceptance) {}
