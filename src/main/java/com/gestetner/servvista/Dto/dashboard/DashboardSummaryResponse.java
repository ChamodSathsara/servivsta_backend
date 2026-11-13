package com.gestetner.servvista.Dto.dashboard;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DashboardSummaryResponse(
        int year,
        long totalBreakdownsYtd,
        long completedBreakdownsYtd,
        long totalServiceVisitsYtd,
        long completedServiceVisitsYtd,
        List<DailyJobSummary> dailyJobs,
        List<TechnicianPerformance> topTechnicians,
        List<OverdueBreakdown> overdueBreakdowns,
        List<OverdueServiceVisit> overdueServiceVisits,
        WarrantySummary warrantySummary) {

    public record DailyJobSummary(LocalDate date, long pending, long completed, long started, long cancelled, long total) {}
    public record TechnicianPerformance(Long technicianId, String techCode, String technicianName, long totalJobs, long completedJobs, double percentage) {}
    public record OverdueBreakdown(Long breakdownId, String breakdownNumber, String machineReferenceNumber, String siteName, LocalDateTime expectedCompletionAt, long overdueDays) {}
    public record OverdueServiceVisit(Long serviceScheduleId, String agreementNumber, String machineReferenceNumber, String siteName, LocalDate expectedVisitDate, long overdueDays) {}
    public record WarrantySummary(Company company, long total, long active, long expiringNext30Days, long expired, long voided) {}
}
