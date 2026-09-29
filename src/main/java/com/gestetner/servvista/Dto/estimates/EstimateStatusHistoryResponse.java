package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateStatus;
import java.time.LocalDateTime;
public record EstimateStatusHistoryResponse(Long historyId, EstimateStatus previousStatus,
                                            EstimateStatus newStatus, Long changedBy,
                                            LocalDateTime changedAt, String reason) {}
