package com.gestetner.servvista.Dto.services;

import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ServiceScheduleResponse(
        Long serviceScheduleId,
        Long agreementId,
        String agreementNumber,
        Long machineId,
        String machineReferenceNumber,
        Integer agreementYearNumber,
        Integer visitNumber,
        LocalDate expectedVisitDate,
        LocalDate scheduledDate,
        LocalDate actualVisitDate,
        ServiceScheduleStatus status,
        Long assignedTechnicianId,
        String assignedTechnicianName,
        String startNote,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        Long solutionTypeId,
        String solutionNote,
        LocalDateTime createdAt
) {
}
