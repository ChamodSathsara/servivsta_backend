package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateStatus;
import java.time.LocalDateTime;
import java.util.List;
public record EstimateResponse(Long estimateId, String estimateNumber, Long machineId,
                               String machineReferenceNumber, Long serviceScheduleId, Long breakdownId,
                               Long technicianId, String technicianName, EstimateStatus status,
                               Long approvedBy, LocalDateTime approvedAt, Long createdBy,
                               LocalDateTime createdAt, List<EstimateLineResponse> lines,
                               List<EstimateStatusHistoryResponse> statusHistory,
                               EstimateAcceptanceResponse acceptance) {}
