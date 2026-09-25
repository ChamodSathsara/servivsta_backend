package com.gestetner.servvista.Dto.agreements;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;

import java.time.LocalDateTime;

public record AgreementStatusHistoryResponse(
        Long historyId,
        AgreementStatus previousStatus,
        AgreementStatus newStatus,
        LocalDateTime changedAt,
        Long changedBy,
        String reason
) {
}
