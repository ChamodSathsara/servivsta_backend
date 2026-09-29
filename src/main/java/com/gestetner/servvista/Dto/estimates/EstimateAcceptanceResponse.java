package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateAcceptedByType;
import java.time.LocalDateTime;
public record EstimateAcceptanceResponse(Long estimateAcceptanceId, EstimateAcceptedByType acceptedByType,
                                         Long portalAccountId, Long recordedByUserId,
                                         LocalDateTime acceptedAt, String referenceNumber, String note) {}
