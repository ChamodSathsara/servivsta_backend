package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateLineStatus;
import jakarta.validation.constraints.*;
public record EstimateLineRequest(@Positive Long partId, @Size(max=150) String pendingPartName,
                                  @NotNull @Positive Integer qty, @Size(max=255) String note,
                                  EstimateLineStatus lineStatus, @Positive Long resolvedBy) {}
