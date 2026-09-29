package com.gestetner.servvista.Dto.machines;

import com.gestetner.servvista.Models.Enums.Machines.MachineStatus;

import java.time.LocalDateTime;

public record MachineStatusHistoryResponse(
        Long machineStatusHistoryId,
        Long machineId,
        String machineReferenceNumber,
        MachineStatus previousStatus,
        MachineStatus newStatus,
        LocalDateTime changedAt,
        Long changedBy,
        String reason
) {
}
