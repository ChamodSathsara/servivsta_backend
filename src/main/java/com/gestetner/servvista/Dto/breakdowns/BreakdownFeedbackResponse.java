package com.gestetner.servvista.Dto.breakdowns;

import java.time.LocalDateTime;

public record BreakdownFeedbackResponse(
        Long feedbackId,
        Long breakdownId,
        Integer rating,
        String comment,
        Long submittedByPortalAccountId,
        LocalDateTime createdAt
) {
}
