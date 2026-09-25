package com.gestetner.servvista.Dto.installations;

import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;

import java.time.LocalDateTime;

public record InstallationStatusHistoryResponse(
        Long historyId,
        InstallationJobStatus previousStatus,
        InstallationJobStatus newStatus,
        LocalDateTime changedAt,
        Long changedBy,
        String note
) {
}
