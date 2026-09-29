package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateAcceptedByType;
import jakarta.validation.constraints.*;
public record EstimateAcceptanceData(@NotNull EstimateAcceptedByType acceptedByType,
                                     @Positive Long portalAccountId,
                                     @Positive Long recordedByUserId,
                                     @Size(max=60) String referenceNumber,
                                     @Size(max=255) String note) {}
