package com.gestetner.servvista.Dto.reports;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ServiceVisitReportRow(
        Long serviceScheduleId,
        String machineReferenceNumber,
        String serialNumber,
        Company company,
        Division division,
        String customerName,
        String siteName,
        Area area,
        String agreementNumber,
        AgreementType agreementType,
        AgreementStatus agreementStatus,
        Integer agreementYearNumber,
        Integer visitNumber,
        ServiceScheduleStatus visitStatus,
        String technicianCode,
        String technicianName,
        LocalDate expectedVisitDate,
        LocalDate scheduledDate,
        LocalDate actualVisitDate,
        String startNote,
        String solutionCode,
        String solutionDescription,
        String solutionNote,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {
}
