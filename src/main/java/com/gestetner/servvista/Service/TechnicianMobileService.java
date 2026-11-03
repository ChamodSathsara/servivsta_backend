package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.breakdowns.BreakdownCompleteRequest;
import com.gestetner.servvista.Dto.breakdowns.BreakdownResponse;
import com.gestetner.servvista.Dto.breakdowns.BreakdownStartRequest;
import com.gestetner.servvista.Dto.breakdowns.BreakdownStartResponse;
import com.gestetner.servvista.Dto.estimates.EstimateRequest;
import com.gestetner.servvista.Dto.estimates.EstimateResponse;
import com.gestetner.servvista.Dto.installations.CompleteInstallationJobRequest;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
import com.gestetner.servvista.Dto.installations.UpdateInstallationJobStatusRequest;
import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;
import com.gestetner.servvista.Dto.services.ServiceVisitCompleteRequest;
import com.gestetner.servvista.Dto.services.ServiceVisitStartRequest;
import com.gestetner.servvista.Dto.services.ServiceVisitStartResponse;
import com.gestetner.servvista.Dto.technician.ChangeOwnPasswordRequest;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownAssignmentStatus;
import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Repositories.breakdowns.BreakdownTechnicianAssignmentRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TechnicianMobileService {
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final BreakdownTechnicianAssignmentRepository assignmentRepository;
    private final InstallationService installationService;
    private final BreakdownService breakdownService;
    private final ServiceScheduleService scheduleService;
    private final EstimateService estimateService;
    private final PasswordEncoder passwordEncoder;

    public TechnicianMobileService(
            TechnicianRepository technicianRepository,
            UserRepository userRepository,
            BreakdownTechnicianAssignmentRepository assignmentRepository,
            InstallationService installationService,
            BreakdownService breakdownService,
            ServiceScheduleService scheduleService,
            EstimateService estimateService,
            PasswordEncoder passwordEncoder) {
        this.technicianRepository = technicianRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.installationService = installationService;
        this.breakdownService = breakdownService;
        this.scheduleService = scheduleService;
        this.estimateService = estimateService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<InstallationJobResponse> installations(Long userId) {
        Long technicianId = technician(userId).getTechnicianId();
        return installationService.getAllJobs().stream()
                .filter(job -> technicianId.equals(job.assignedTechnicianId()))
                .toList();
    }

    public InstallationJobResponse updateInstallationStatus(
            Long userId, Long jobId, InstallationJobStatus status, String note) {
        ensureInstallationOwner(userId, jobId);
        if (status != InstallationJobStatus.IN_PROGRESS
                && status != InstallationJobStatus.SUBMITTED) {
            throw new AccessDeniedException(
                    "Technicians may only move installation jobs to IN_PROGRESS or SUBMITTED");
        }
        return installationService.changeJobStatus(jobId,
                new UpdateInstallationJobStatusRequest(status, userId, note));
    }

    public InstallationJobResponse completeInstallation(Long userId, Long jobId, String note) {
        ensureInstallationOwner(userId, jobId);
        return installationService.completeJob(jobId,
                new CompleteInstallationJobRequest(userId, note));
    }

    @Transactional(readOnly = true)
    public List<BreakdownResponse> breakdowns(Long userId) {
        Long technicianId = technician(userId).getTechnicianId();
        return breakdownService.getAll().stream()
                .filter(item -> ownsBreakdown(technicianId, item.breakdownId()))
                .toList();
    }

    public BreakdownStartResponse startBreakdown(
            Long userId, Long breakdownId, BreakdownStartRequest request) {
        ensureBreakdownOwner(userId, breakdownId);
        return breakdownService.start(breakdownId, new BreakdownStartRequest(
                request.startNote(), request.meterCounterTypeId(), request.meterReading(),
                userId, request.meterReadingNote()));
    }

    public BreakdownResponse completeBreakdown(
            Long userId, Long breakdownId, BreakdownCompleteRequest request) {
        ensureBreakdownOwner(userId, breakdownId);
        return breakdownService.complete(breakdownId, request);
    }

    @Transactional(readOnly = true)
    public List<ServiceScheduleResponse> schedules(Long userId) {
        Long technicianId = technician(userId).getTechnicianId();
        return scheduleService.getAll().stream()
                .filter(item -> technicianId.equals(item.assignedTechnicianId()))
                .toList();
    }

    public ServiceVisitStartResponse startSchedule(
            Long userId, Long scheduleId, ServiceVisitStartRequest request) {
        ensureScheduleOwner(userId, scheduleId);
        return scheduleService.start(scheduleId, new ServiceVisitStartRequest(
                request.startNote(), request.meterCounterTypeId(), request.meterReading(),
                userId, request.meterReadingNote()));
    }

    public ServiceScheduleResponse completeSchedule(
            Long userId, Long scheduleId, ServiceVisitCompleteRequest request) {
        ensureScheduleOwner(userId, scheduleId);
        return scheduleService.complete(scheduleId, request);
    }

    public EstimateResponse createEstimate(Long userId, EstimateRequest request) {
        Technician technician = technician(userId);
        if (request.breakdownId() != null) {
            ensureBreakdownOwner(userId, request.breakdownId());
        }
        if (request.serviceScheduleId() != null) {
            ensureScheduleOwner(userId, request.serviceScheduleId());
        }
        EstimateRequest scoped = new EstimateRequest(
                request.machineId(), request.serviceScheduleId(), request.breakdownId(),
                technician.getTechnicianId(), null, null, userId,
                request.statusReason(), request.lines(), null);
        return estimateService.create(scoped);
    }

    public void changePassword(Long userId, ChangeOwnPasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " was not found"));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("New password must be different from the current password");
        }
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(userId);
        userRepository.saveAndFlush(user);
    }

    private Technician technician(Long userId) {
        return technicianRepository.findByUserId(userId)
                .orElseThrow(() -> new AccessDeniedException(
                        "The authenticated user does not have a technician profile"));
    }

    private void ensureInstallationOwner(Long userId, Long jobId) {
        Long technicianId = technician(userId).getTechnicianId();
        InstallationJobResponse job = installationService.getJobById(jobId);
        if (!technicianId.equals(job.assignedTechnicianId())) {
            throw new AccessDeniedException("This installation job is not assigned to you");
        }
    }

    private void ensureBreakdownOwner(Long userId, Long breakdownId) {
        if (!ownsBreakdown(technician(userId).getTechnicianId(), breakdownId)) {
            throw new AccessDeniedException("This breakdown is not assigned to you");
        }
    }

    private boolean ownsBreakdown(Long technicianId, Long breakdownId) {
        return assignmentRepository.existsByBreakdownIdAndTechnicianIdAndAssignmentStatus(
                breakdownId, technicianId, BreakdownAssignmentStatus.CURRENT);
    }

    private void ensureScheduleOwner(Long userId, Long scheduleId) {
        Long technicianId = technician(userId).getTechnicianId();
        ServiceScheduleResponse schedule = scheduleService.getById(scheduleId);
        if (!technicianId.equals(schedule.assignedTechnicianId())) {
            throw new AccessDeniedException("This service visit is not assigned to you");
        }
    }
}
