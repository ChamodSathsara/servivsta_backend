package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.breakdowns.BreakdownCompleteRequest;
import com.gestetner.servvista.Dto.breakdowns.BreakdownResponse;
import com.gestetner.servvista.Dto.breakdowns.BreakdownStartRequest;
import com.gestetner.servvista.Dto.breakdowns.BreakdownStartResponse;
import com.gestetner.servvista.Dto.estimates.EstimateRequest;
import com.gestetner.servvista.Dto.estimates.EstimateResponse;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;
import com.gestetner.servvista.Dto.services.ServiceVisitCompleteRequest;
import com.gestetner.servvista.Dto.services.ServiceVisitStartRequest;
import com.gestetner.servvista.Dto.services.ServiceVisitStartResponse;
import com.gestetner.servvista.Dto.technician.ChangeOwnPasswordRequest;
import com.gestetner.servvista.Dto.technician.TechnicianInstallationStatusRequest;
import com.gestetner.servvista.Dto.technician.TechnicianOpenWorkResponse;
import com.gestetner.servvista.Dto.technician.TechnicianYtdPerformanceResponse;
import com.gestetner.servvista.Dto.technician.TechnicianEstimateOptionsResponse;
import com.gestetner.servvista.Dto.reference.MeterCounterTypeResponse;
import com.gestetner.servvista.Dto.reference.SolutionTypeResponse;
import com.gestetner.servvista.Dto.meters.MeterReadingResponse;
import com.gestetner.servvista.Dto.organization.CityResponse;
import com.gestetner.servvista.Dto.sales.NewSaleWorkflowDtos.WorkflowResponse;
import com.gestetner.servvista.Service.CityService;
import com.gestetner.servvista.Service.NewSaleWorkflowService;
import com.gestetner.servvista.Service.MeterCounterTypeService;
import com.gestetner.servvista.Service.SolutionTypeService;
import com.gestetner.servvista.Service.TechnicianMobileService;
import com.gestetner.servvista.Service.MachineModelService;
import com.gestetner.servvista.Service.PartCategoryService;
import com.gestetner.servvista.Service.PartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/technician/me")
@PreAuthorize("hasRole('TECHNICIAN')")
public class TechnicianMobileController {
    private final TechnicianMobileService service;
    private final MeterCounterTypeService meterCounterTypeService;
    private final SolutionTypeService solutionTypeService;
    private final CityService cityService;
    private final NewSaleWorkflowService newSaleWorkflowService;
    private final MachineModelService machineModelService;
    private final PartCategoryService partCategoryService;
    private final PartService partService;

    public TechnicianMobileController(
            TechnicianMobileService service,
            MeterCounterTypeService meterCounterTypeService,
            SolutionTypeService solutionTypeService,
            CityService cityService,
            NewSaleWorkflowService newSaleWorkflowService,
            MachineModelService machineModelService,
            PartCategoryService partCategoryService,
            PartService partService) {
        this.service = service;
        this.meterCounterTypeService = meterCounterTypeService;
        this.solutionTypeService = solutionTypeService;
        this.cityService = cityService;
        this.newSaleWorkflowService = newSaleWorkflowService;
        this.machineModelService = machineModelService;
        this.partCategoryService = partCategoryService;
        this.partService = partService;
    }

    @GetMapping("/cities")
    public List<CityResponse> cities() { return cityService.getAll(); }

    @GetMapping("/new-sale-workflows")
    public List<WorkflowResponse> newSaleWorkflows(@AuthenticationPrincipal Jwt jwt) {
        return newSaleWorkflowService.getForTechnicianUser(userId(jwt));
    }

    @GetMapping("/installation-jobs")
    public List<InstallationJobResponse> installations(@AuthenticationPrincipal Jwt jwt) {
        return service.installations(userId(jwt));
    }

    @GetMapping("/installation-jobs/recent")
    public List<InstallationJobResponse> recentInstallations(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "7") int days) {
        return service.recentInstallations(userId(jwt), days);
    }

    @GetMapping("/installation-jobs/{jobId}")
    public InstallationJobResponse installation(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long jobId) {
        return service.installation(userId(jwt), jobId);
    }

    @PatchMapping("/installation-jobs/{jobId}/status")
    public InstallationJobResponse updateInstallationStatus(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long jobId,
            @Valid @RequestBody TechnicianInstallationStatusRequest request) {
        return service.updateInstallationStatus(
                userId(jwt), jobId, request.status(), request.note());
    }

    @PostMapping("/installation-jobs/{jobId}/complete")
    public InstallationJobResponse completeInstallation(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long jobId,
            @RequestBody(required = false) TechnicianInstallationStatusRequest request) {
        return service.completeInstallation(
                userId(jwt), jobId, request == null ? null : request.note());
    }

    @GetMapping("/breakdowns")
    public List<BreakdownResponse> breakdowns(@AuthenticationPrincipal Jwt jwt) {
        return service.breakdowns(userId(jwt));
    }

    @GetMapping("/breakdowns/recent")
    public List<BreakdownResponse> recentBreakdowns(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "7") int days) {
        return service.recentBreakdowns(userId(jwt), days);
    }

    @GetMapping("/breakdowns/{breakdownId}")
    public BreakdownResponse breakdown(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long breakdownId) {
        return service.breakdown(userId(jwt), breakdownId);
    }

    @PatchMapping("/breakdowns/{breakdownId}/start")
    public BreakdownStartResponse startBreakdown(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long breakdownId,
            @Valid @RequestBody BreakdownStartRequest request) {
        return service.startBreakdown(userId(jwt), breakdownId, request);
    }

    @PatchMapping("/breakdowns/{breakdownId}/complete")
    public BreakdownResponse completeBreakdown(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long breakdownId,
            @Valid @RequestBody BreakdownCompleteRequest request) {
        return service.completeBreakdown(userId(jwt), breakdownId, request);
    }

    @GetMapping("/service-schedules")
    public List<ServiceScheduleResponse> schedules(@AuthenticationPrincipal Jwt jwt) {
        return service.schedules(userId(jwt));
    }

    @GetMapping("/service-schedules/current-month")
    public List<ServiceScheduleResponse> currentMonthSchedules(
            @AuthenticationPrincipal Jwt jwt) {
        return service.currentMonthSchedules(userId(jwt));
    }

    @GetMapping("/open-work")
    public TechnicianOpenWorkResponse openWork(@AuthenticationPrincipal Jwt jwt) {
        return service.openWork(userId(jwt));
    }

    @GetMapping("/performance/ytd")
    public TechnicianYtdPerformanceResponse ytdPerformance(@AuthenticationPrincipal Jwt jwt) {
        return service.ytdPerformance(userId(jwt));
    }

    @GetMapping("/service-schedules/{scheduleId}")
    public ServiceScheduleResponse schedule(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long scheduleId) {
        return service.schedule(userId(jwt), scheduleId);
    }

    @PatchMapping("/service-schedules/{scheduleId}/start")
    public ServiceVisitStartResponse startSchedule(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long scheduleId,
            @Valid @RequestBody ServiceVisitStartRequest request) {
        return service.startSchedule(userId(jwt), scheduleId, request);
    }

    @PatchMapping("/service-schedules/{scheduleId}/complete")
    public ServiceScheduleResponse completeSchedule(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long scheduleId,
            @Valid @RequestBody ServiceVisitCompleteRequest request) {
        return service.completeSchedule(userId(jwt), scheduleId, request);
    }

    @PostMapping("/estimates")
    public EstimateResponse createEstimate(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody EstimateRequest request) {
        return service.createEstimate(userId(jwt), request);
    }

    @GetMapping("/estimate-options")
    public TechnicianEstimateOptionsResponse estimateOptions(@AuthenticationPrincipal Jwt jwt) {
        return new TechnicianEstimateOptionsResponse(
                service.estimateMachines(userId(jwt)),
                machineModelService.getAll().stream().filter(x -> Boolean.TRUE.equals(x.isActive())).toList(),
                partCategoryService.getAll().stream().filter(x -> Boolean.TRUE.equals(x.isActive())).toList(),
                partService.getAll().stream().filter(x -> Boolean.TRUE.equals(x.isActive())).toList());
    }

    @GetMapping("/service-schedules/recent")
    public List<ServiceScheduleResponse> recentSchedules(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "30") int days) {
        return service.recentSchedules(userId(jwt), days);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ChangeOwnPasswordRequest request) {
        service.changePassword(userId(jwt), request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meter-counter-types")
    public List<MeterCounterTypeResponse> meterCounterTypes() {
        return meterCounterTypeService.getAll();
    }

    @GetMapping("/solution-types")
    public List<SolutionTypeResponse> solutionTypes() {
        return solutionTypeService.getAll();
    }

    @GetMapping("/meter-readings/machine/{machineReferenceNumber}")
    public List<MeterReadingResponse> meterReadings(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String machineReferenceNumber) {
        return service.meterReadings(userId(jwt), machineReferenceNumber);
    }

    private Long userId(Jwt jwt) {
        Object claim = jwt.getClaim("userId");
        if (!(claim instanceof Number number)) {
            throw new IllegalStateException("The access token does not contain a valid userId");
        }
        return number.longValue();
    }
}
