package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.breakdowns.BreakdownResponse;
import com.gestetner.servvista.Dto.dashboard.DashboardSummaryResponse;
import com.gestetner.servvista.Dto.identity.TechnicianUserResponse;
import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownAssignmentStatus;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import com.gestetner.servvista.Models.Enums.Machines.WarrantyStatus;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import com.gestetner.servvista.Models.entity.machines.MachineWarranty;
import com.gestetner.servvista.Repositories.breakdowns.BreakdownTechnicianAssignmentRepository;
import com.gestetner.servvista.Repositories.machines.MachineWarrantyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final BreakdownService breakdownService;
    private final ServiceScheduleService scheduleService;
    private final StaffUserService staffUserService;
    private final BreakdownTechnicianAssignmentRepository assignmentRepository;
    private final MachineWarrantyRepository warrantyRepository;

    public DashboardService(BreakdownService breakdownService, ServiceScheduleService scheduleService,
            StaffUserService staffUserService, BreakdownTechnicianAssignmentRepository assignmentRepository,
            MachineWarrantyRepository warrantyRepository) {
        this.breakdownService = breakdownService;
        this.scheduleService = scheduleService;
        this.staffUserService = staffUserService;
        this.assignmentRepository = assignmentRepository;
        this.warrantyRepository = warrantyRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse summary(Company company) {
        LocalDate today = LocalDate.now();
        LocalDate yearStart = today.withDayOfYear(1);
        LocalDateTime yearStartTime = yearStart.atStartOfDay();
        List<BreakdownResponse> breakdowns = breakdownService.getAll().stream()
                .filter(x -> x.createdAt() != null && !x.createdAt().isBefore(yearStartTime) && !x.createdAt().toLocalDate().isAfter(today)).toList();
        List<ServiceScheduleResponse> visits = scheduleService.getAll().stream()
                .filter(x -> x.expectedVisitDate() != null && !x.expectedVisitDate().isBefore(yearStart) && !x.expectedVisitDate().isAfter(today)).toList();

        List<DashboardSummaryResponse.DailyJobSummary> daily = new ArrayList<>();
        for (int offset = 10; offset >= 0; offset--) {
            LocalDate date = today.minusDays(offset);
            List<BreakdownResponse> dayBreakdowns = breakdowns.stream().filter(x -> x.createdAt().toLocalDate().equals(date)).toList();
            List<ServiceScheduleResponse> dayVisits = visits.stream().filter(x -> x.expectedVisitDate().equals(date)).toList();
            long completed = dayBreakdowns.stream().filter(x -> x.status() == BreakdownStatus.COMPLETED).count()
                    + dayVisits.stream().filter(x -> x.status() == ServiceScheduleStatus.COMPLETED).count();
            long started = dayBreakdowns.stream().filter(x -> x.status() == BreakdownStatus.STARTED).count()
                    + dayVisits.stream().filter(x -> x.status() == ServiceScheduleStatus.STARTED).count();
            long cancelled = dayBreakdowns.stream().filter(x -> x.status() == BreakdownStatus.CANCELLED).count()
                    + dayVisits.stream().filter(x -> x.status() == ServiceScheduleStatus.CANCELLED).count();
            long total = dayBreakdowns.size() + dayVisits.size();
            daily.add(new DashboardSummaryResponse.DailyJobSummary(date, total - completed - started - cancelled, completed, started, cancelled, total));
        }

        Map<Long, TechnicianUserResponse> technicians = staffUserService.getTechnicians().stream()
                .collect(Collectors.toMap(TechnicianUserResponse::technicianId, Function.identity()));
        Map<Long, long[]> scores = new HashMap<>();
        assignmentRepository.findAll().stream()
                .filter(x -> x.getAssignmentStatus() == BreakdownAssignmentStatus.CURRENT)
                .filter(x -> breakdowns.stream().anyMatch(b -> b.breakdownId().equals(x.getBreakdownId())))
                .forEach(x -> {
                    BreakdownResponse b = breakdowns.stream().filter(v -> v.breakdownId().equals(x.getBreakdownId())).findFirst().orElseThrow();
                    long[] score = scores.computeIfAbsent(x.getTechnicianId(), key -> new long[2]); score[0]++; if (b.status() == BreakdownStatus.COMPLETED) score[1]++;
                });
        visits.forEach(v -> { if (v.assignedTechnicianId() != null) { long[] score = scores.computeIfAbsent(v.assignedTechnicianId(), key -> new long[2]); score[0]++; if (v.status() == ServiceScheduleStatus.COMPLETED) score[1]++; }});
        List<DashboardSummaryResponse.TechnicianPerformance> top = scores.entrySet().stream().map(entry -> {
            TechnicianUserResponse t = technicians.get(entry.getKey()); long total = entry.getValue()[0], completed = entry.getValue()[1];
            return new DashboardSummaryResponse.TechnicianPerformance(entry.getKey(), t == null ? "—" : t.techCode(), t == null ? "Unknown" : t.user().userName(), total, completed, total == 0 ? 0 : Math.round(completed * 1000.0 / total) / 10.0);
        }).sorted(Comparator.comparingDouble(DashboardSummaryResponse.TechnicianPerformance::percentage).reversed().thenComparing(DashboardSummaryResponse.TechnicianPerformance::completedJobs, Comparator.reverseOrder())).limit(5).toList();

        List<DashboardSummaryResponse.OverdueBreakdown> overdueBreakdowns = breakdownService.getAll().stream()
                .filter(x -> x.expectedCompletionAt() != null && x.expectedCompletionAt().isBefore(LocalDateTime.now()))
                .filter(x -> x.status() != BreakdownStatus.COMPLETED && x.status() != BreakdownStatus.CANCELLED)
                .sorted(Comparator.comparing(BreakdownResponse::expectedCompletionAt)).limit(5)
                .map(x -> new DashboardSummaryResponse.OverdueBreakdown(x.breakdownId(), x.breakdownNumber(), x.machineReferenceNumber(), x.siteName(), x.expectedCompletionAt(), ChronoUnit.DAYS.between(x.expectedCompletionAt().toLocalDate(), today))).toList();
        List<DashboardSummaryResponse.OverdueServiceVisit> overdueVisits = scheduleService.getAll().stream()
                .filter(x -> x.expectedVisitDate() != null && x.expectedVisitDate().isBefore(today))
                .filter(x -> x.status() != ServiceScheduleStatus.COMPLETED && x.status() != ServiceScheduleStatus.CANCELLED)
                .sorted(Comparator.comparing(ServiceScheduleResponse::expectedVisitDate)).limit(5)
                .map(x -> new DashboardSummaryResponse.OverdueServiceVisit(x.serviceScheduleId(), x.agreementNumber(), x.machineReferenceNumber(), x.siteName(), x.expectedVisitDate(), ChronoUnit.DAYS.between(x.expectedVisitDate(), today))).toList();

        List<MachineWarranty> warranties = warrantyRepository.findAll().stream()
                .filter(x -> x.getMachine() != null && (company == null || x.getMachine().getCompany() == company))
                .filter(x -> !x.getEndDate().isBefore(yearStart) && !x.getStartDate().isAfter(today)).toList();
        DashboardSummaryResponse.WarrantySummary warranty = new DashboardSummaryResponse.WarrantySummary(company, warranties.size(),
                warranties.stream().filter(x -> x.getStatus() == WarrantyStatus.ACTIVE && !x.getEndDate().isBefore(today)).count(),
                warranties.stream().filter(x -> x.getStatus() == WarrantyStatus.ACTIVE && !x.getEndDate().isBefore(today) && !x.getEndDate().isAfter(today.plusDays(30))).count(),
                warranties.stream().filter(x -> x.getStatus() == WarrantyStatus.EXPIRED || x.getEndDate().isBefore(today)).count(),
                warranties.stream().filter(x -> x.getStatus() == WarrantyStatus.VOID).count());

        return new DashboardSummaryResponse(today.getYear(), breakdowns.size(), breakdowns.stream().filter(x -> x.status() == BreakdownStatus.COMPLETED).count(), visits.size(), visits.stream().filter(x -> x.status() == ServiceScheduleStatus.COMPLETED).count(), daily, top, overdueBreakdowns, overdueVisits, warranty);
    }
}
