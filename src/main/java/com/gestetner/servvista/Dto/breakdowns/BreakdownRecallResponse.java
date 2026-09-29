package com.gestetner.servvista.Dto.breakdowns;

import java.time.LocalDateTime;

public record BreakdownRecallResponse(
        Long breakdownRecallId,
        Long breakdownId,
        String recallNumber,
        LocalDateTime recallDate,
        String recallReason,
        Long recalledBy,
        LocalDateTime recalledAt
) {
}
