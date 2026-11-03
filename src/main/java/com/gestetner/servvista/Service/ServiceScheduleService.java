package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;
import com.gestetner.servvista.Dto.services.ServiceVisitCompleteRequest;
import com.gestetner.servvista.Dto.services.ServiceVisitMeterReadingResponse;
import com.gestetner.servvista.Dto.services.ServiceVisitStartRequest;
import com.gestetner.servvista.Dto.services.ServiceVisitStartResponse;
import com.gestetner.servvista.Models.Enums.Meters.MeterReadingSource;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.meters.MeterReading;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import com.gestetner.servvista.Repositories.agreements.MachineAgreementRepository;
import com.gestetner.servvista.Repositories.installations.InstallationJobRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.meters.MeterCounterTypeRepository;
import com.gestetner.servvista.Repositories.meters.MeterReadingRepository;
import com.gestetner.servvista.Repositories.services.ServiceScheduleRepository;
import com.gestetner.servvista.Repositories.services.SolutionTypeRepository;
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
    private final MeterCounterTypeRepository meterCounterTypeRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final UserRepository userRepository;
    private final SolutionTypeRepository solutionTypeRepository;

    public ServiceScheduleService(
            ServiceScheduleRepository scheduleRepository,
            MachineAgreementRepository agreementRepository,
            MachineRepository machineRepository,
            InstallationJobRepository installationJobRepository,
            TechnicianRepository technicianRepository,
            MeterCounterTypeRepository meterCounterTypeRepository,
            MeterReadingRepository meterReadingRepository,
            UserRepository userRepository,
            SolutionTypeRepository solutionTypeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.agreementRepository = agreementRepository;
        this.machineRepository = machineRepository;
        this.installationJobRepository = installationJobRepository;
        this.technicianRepository = technicianRepository;
        this.meterCounterTypeRepository = meterCounterTypeRepository;
        this.meterReadingRepository = meterReadingRepository;
        this.userRepository = userRepository;
        this.solutionTypeRepository = solutionTypeRepository;
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

    public ServiceVisitStartResponse start(Long scheduleId, ServiceVisitStartRequest request) {
        ServiceSchedule schedule = findSchedule(scheduleId);
        if (schedule.getStatus() != ServiceScheduleStatus.SCHEDULED
                && schedule.getStatus() != ServiceScheduleStatus.DUE) {
            throw new IllegalStateException(
                    "Only a scheduled or due service visit can be started");
        }
        requireExists(meterCounterTypeRepository.existsById(request.meterCounterTypeId()),
                "Meter counter type", request.meterCounterTypeId());
        requireExists(userRepository.existsById(request.capturedByUserId()),
                "Capturing user", request.capturedByUserId());

        LocalDateTime now = LocalDateTime.now();
        schedule.setStatus(ServiceScheduleStatus.STARTED);
        schedule.setStartNote(optional(request.startNote()));
        schedule.setStartedAt(now);
        schedule.setActualVisitDate(now.toLocalDate());
        scheduleRepository.saveAndFlush(schedule);

        MeterReading reading = new MeterReading();
        reading.setMachineId(schedule.getMachineId());
        reading.setMeterCounterTypeId(request.meterCounterTypeId());
        reading.setReadingValue(request.meterReading());
        reading.setReadingDatetime(now);
        reading.setSourceCode(MeterReadingSource.SERVICE);
        reading.setServiceScheduleId(scheduleId);
        reading.setCapturedByUserId(request.capturedByUserId());
        reading.setNote(optional(request.meterReadingNote()));
        reading.setCreatedAt(now);
        reading = meterReadingRepository.saveAndFlush(reading);

        return new ServiceVisitStartResponse(
                response(findSchedule(scheduleId)), meterReadingResponse(reading));
    }

    public ServiceScheduleResponse complete(
            Long scheduleId, ServiceVisitCompleteRequest request) {
        ServiceSchedule schedule = findSchedule(scheduleId);
        if (schedule.getStatus() != ServiceScheduleStatus.STARTED) {
            throw new IllegalStateException("Only a started service visit can be completed");
        }
        requireExists(solutionTypeRepository.existsById(request.solutionTypeId()),
                "Solution type", request.solutionTypeId());

        schedule.setStatus(ServiceScheduleStatus.COMPLETED);
        schedule.setSolutionTypeId(request.solutionTypeId());
        schedule.setSolutionNote(optional(request.solutionNote()));
        schedule.setCompletedAt(LocalDateTime.now());
        scheduleRepository.saveAndFlush(schedule);
        return response(findSchedule(scheduleId));
    }

    private ServiceSchedule findSchedule(Long scheduleId) {
        return scheduleRepository.findByIdWithDetails(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Service schedule " + scheduleId + " was not found"));
    }

    private ServiceVisitMeterReadingResponse meterReadingResponse(MeterReading reading) {
        return new ServiceVisitMeterReadingResponse(
                reading.getMeterReadingId(), reading.getMachineId(),
                reading.getMeterCounterTypeId(), reading.getReadingValue(),
                reading.getReadingDatetime(), reading.getSourceCode(),
                reading.getServiceScheduleId(), reading.getCapturedByUserId(),
                reading.getNote(), reading.getCreatedAt());
    }

    private void requireExists(boolean exists, String name, Long id) {
        if (!exists) {
            throw new EntityNotFoundException(name + " " + id + " was not found");
        }
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
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
