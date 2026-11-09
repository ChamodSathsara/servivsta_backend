package com.gestetner.servvista.Dto.reports;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public record BreakdownJobReportRow(
        Long breakdownId,
        String breakdownNumber,
        String machineReferenceNumber,
        String serialNumber,
        Company company,
        Division division,
        String customerName,
        String siteName,
        Area area,
        AgreementType agreementType,
        AgreementStatus agreementStatus,
        BreakdownStatus jobStatus,
        String technicianCode,
        String technicianName,
        String reportedNote,
        String solutionNote,
        String contactEmail,
        LocalDateTime expectedCompletionAt,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        Double responseTimeHours
) {
    public BreakdownJobReportRow(
            Long breakdownId, String breakdownNumber, String machineReferenceNumber,
            String serialNumber, Company company, Division division, String customerName,
            String siteName, Area area, AgreementType agreementType,
            AgreementStatus agreementStatus, BreakdownStatus jobStatus,
            String technicianCode, String technicianName, String reportedNote,
            String solutionNote, String contactEmail, LocalDateTime expectedCompletionAt,
            LocalDateTime createdAt, LocalDateTime startedAt, LocalDateTime completedAt) {
        this(breakdownId, breakdownNumber, machineReferenceNumber, serialNumber, company,
                division, customerName, siteName, area, agreementType, agreementStatus,
                jobStatus, technicianCode, technicianName, reportedNote, solutionNote,
                contactEmail, expectedCompletionAt, createdAt, startedAt, completedAt,
                workingHours(createdAt, startedAt));
    }

    private static Double workingHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || end.isBefore(start)) return null;

        LocalTime workStart = LocalTime.of(8, 45);
        LocalTime workEnd = LocalTime.of(17, 15);
        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(12, 30);
        long totalMinutes = 0;

        for (LocalDate day = start.toLocalDate();
             !day.isAfter(end.toLocalDate());
             day = day.plusDays(1)) {
            if (day.getDayOfWeek().getValue() > 5) continue;

            LocalDateTime from = day.atTime(workStart);
            LocalDateTime to = day.atTime(workEnd);
            if (start.isAfter(from)) from = start;
            if (end.isBefore(to)) to = end;

            if (to.isAfter(from)) {
                long minutes = Duration.between(from, to).toMinutes();
                LocalDateTime lunchFrom = day.atTime(lunchStart);
                LocalDateTime lunchTo = day.atTime(lunchEnd);
                LocalDateTime overlapStart = from.isAfter(lunchFrom) ? from : lunchFrom;
                LocalDateTime overlapEnd = to.isBefore(lunchTo) ? to : lunchTo;
                if (overlapEnd.isAfter(overlapStart)) {
                    minutes -= Duration.between(overlapStart, overlapEnd).toMinutes();
                }
                totalMinutes += minutes;
            }
        }
        return totalMinutes / 60.0;
    }
}
