package com.gestetner.servvista.Dto.estimates;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateLineStatus;
import java.time.LocalDateTime;
public record EstimateLineResponse(Long estimateLineId, Long partId, String partCode,
                                   String pendingPartName, Integer qty, String note,
                                   EstimateLineStatus lineStatus, Long resolvedBy,
                                   LocalDateTime resolvedAt) {}
