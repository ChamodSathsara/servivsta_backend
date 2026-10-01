package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import com.gestetner.servvista.Repositories.agreements.MachineAgreementRepository;
import com.gestetner.servvista.Repositories.installations.InstallationJobRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.services.ServiceScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServiceScheduleService {

    private final ServiceScheduleRepository scheduleRepository;
    private final MachineAgreementRepository agreementRepository;
    private final MachineRepository machineRepository;
    private final InstallationJobRepository installationJobRepository;
    private final TechnicianRepository technicianRepository;

    public ServiceScheduleService(
            ServiceScheduleRepository scheduleRepository,
            MachineAgreementRepository agreementRepository,
            MachineRepository machineRepository,
            InstallationJobRepository installationJobRepository,
            TechnicianRepository technicianRepository) {
        this.scheduleRepository = scheduleRepository;
        this.agreementRepository = agreementRepository;
        this.machineRepository = machineRepository;
        this.installationJobRepository = installationJobRepository;
        this.technicianRepository = technicianRepository;
    }

    public List<ServiceScheduleResponse> generateForSubmission(
            Long machineId, Long installationJobId, LocalDateTime createdAt) {
        MachineAgreement agreement = agreementRepository
                .findFirstByMachineIdAndInstallationJobIdAndIsActiveTrueOrderByAgreementIdDesc(
                        machineId, installationJobId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "An active agreement was not found for machine " + machineId
                                + " and installation job " + installationJobId));

        if (scheduleRepository.existsByAgreementId(agreement.getAgreementId())) {
            throw new IllegalStateException(
                    "Service schedules already exist for agreement " + agreement.getAgreementId());
        }

        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Machine " + machineId + " was not found"));
        Long assignedTechnicianId = machine.getCurrentServiceTechnicianId();
        if (assignedTechnicianId == null) {
            assignedTechnicianId = installationJobRepository.findById(installationJobId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Installation job " + installationJobId + " was not found"))
                    .getAssignedTechnicianId();
        }

        List<ServiceSchedule> schedules = new ArrayList<>();
        for (int year = 1; year <= agreement.getAgreementPeriodYears(); year++) {
            for (int visit = 1; visit <= agreement.getVisitsPerYear(); visit++) {
                long monthsFromStart = ((long) (year - 1) * 12L)
                        + Math.round((12.0 * visit) / agreement.getVisitsPerYear());
                LocalDate expectedDate = agreement.getAgreementStartDate()
                        .plusMonths(monthsFromStart);
                if (expectedDate.isAfter(agreement.getAgreementEndDate())) {
                    continue;
                }

                ServiceSchedule schedule = new ServiceSchedule();
                schedule.setAgreementId(agreement.getAgreementId());
                schedule.setMachineId(machineId);
                schedule.setAgreementYearNumber(year);
                schedule.setVisitNumber(visit);
                schedule.setExpectedVisitDate(expectedDate);
                schedule.setStatus(ServiceScheduleStatus.SCHEDULED);
                schedule.setAssignedTechnicianId(assignedTechnicianId);
                schedule.setCreatedAt(createdAt);
                schedules.add(schedule);
            }
        }

        scheduleRepository.saveAllAndFlush(schedules);
        return scheduleRepository.findAllByAgreementIdWithDetails(agreement.getAgreementId())
                .stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public List<ServiceScheduleResponse> getAll() {
        return scheduleRepository.findAllWithDetails().stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public ServiceScheduleResponse getById(Long scheduleId) {
        return response(scheduleRepository.findByIdWithDetails(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Service schedule " + scheduleId + " was not found")));
    }

    @Transactional(readOnly = true)
    public List<ServiceScheduleResponse> getByAgreement(Long agreementId) {
        if (!agreementRepository.existsById(agreementId)) {
            throw new EntityNotFoundException("Machine agreement " + agreementId + " was not found");
        }
        return scheduleRepository.findAllByAgreementIdWithDetails(agreementId)
                .stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public List<ServiceScheduleResponse> getByMachine(Long machineId) {
        if (!machineRepository.existsById(machineId)) {
            throw new EntityNotFoundException("Machine " + machineId + " was not found");
        }
        return scheduleRepository.findAllByMachineIdWithDetails(machineId)
                .stream().map(this::response).toList();
    }

    private ServiceScheduleResponse response(ServiceSchedule schedule) {
        MachineAgreement agreement = schedule.getAgreement() != null
                ? schedule.getAgreement()
                : agreementRepository.findById(schedule.getAgreementId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Machine agreement " + schedule.getAgreementId() + " was not found"));
        Machine machine = schedule.getMachine() != null
                ? schedule.getMachine()
                : machineRepository.findById(schedule.getMachineId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Machine " + schedule.getMachineId() + " was not found"));
        String technicianName = schedule.getAssignedTechnicianId() == null
                ? null
                : schedule.getAssignedTechnician() != null
                        ? schedule.getAssignedTechnician().getTechnicianName()
                        : technicianRepository.findById(schedule.getAssignedTechnicianId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Technician " + schedule.getAssignedTechnicianId()
                                                + " was not found"))
                                .getTechnicianName();
        return new ServiceScheduleResponse(
                schedule.getServiceScheduleId(), schedule.getAgreementId(),
                agreement.getAgreementNumber(), schedule.getMachineId(),
                machine.getMachineReferenceNumber(),
                schedule.getAgreementYearNumber(), schedule.getVisitNumber(),
                schedule.getExpectedVisitDate(), schedule.getScheduledDate(),
                schedule.getActualVisitDate(), schedule.getStatus(),
                schedule.getAssignedTechnicianId(), technicianName,
                schedule.getStartNote(), schedule.getStartedAt(), schedule.getCompletedAt(),
                schedule.getSolutionTypeId(), schedule.getSolutionNote(), schedule.getCreatedAt());
    }
}
