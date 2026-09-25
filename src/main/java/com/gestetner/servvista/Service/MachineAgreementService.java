package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.agreements.AgreementStatusHistoryResponse;
import com.gestetner.servvista.Dto.agreements.MachineAgreementRequest;
import com.gestetner.servvista.Dto.agreements.MachineAgreementResponse;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.entity.agreements.AgreementStatusHistory;
import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import com.gestetner.servvista.Repositories.agreements.AgreementStatusHistoryRepository;
import com.gestetner.servvista.Repositories.agreements.MachineAgreementRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.installations.InstallationJobRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MachineAgreementService {

    private final MachineAgreementRepository agreementRepository;
    private final AgreementStatusHistoryRepository historyRepository;
    private final MachineRepository machineRepository;
    private final InstallationJobRepository installationJobRepository;
    private final UserRepository userRepository;

    public MachineAgreementService(
            MachineAgreementRepository agreementRepository,
            AgreementStatusHistoryRepository historyRepository,
            MachineRepository machineRepository,
            InstallationJobRepository installationJobRepository,
            UserRepository userRepository) {
        this.agreementRepository = agreementRepository;
        this.historyRepository = historyRepository;
        this.machineRepository = machineRepository;
        this.installationJobRepository = installationJobRepository;
        this.userRepository = userRepository;
    }

    public MachineAgreementResponse create(MachineAgreementRequest request) {
        validateRequest(request, null);
        AgreementStatus status = request.agreementStatus() == null
                ? AgreementStatus.ACTIVE
                : request.agreementStatus();
        LocalDateTime now = LocalDateTime.now();

        try {
            MachineAgreement agreement = new MachineAgreement();
            agreement.setAgreementNumber(nextAgreementNumber(request.agreementType().name()));
            apply(agreement, request);
            agreement.setAgreementStatus(status);
            agreement.setIsActive(request.isActive() == null || request.isActive());
            agreement.setCreatedBy(request.performedBy());
            agreement.setCreatedAt(now);
            agreement = agreementRepository.saveAndFlush(agreement);

            addHistory(
                    agreement.getAgreementId(),
                    null,
                    status,
                    request.performedBy(),
                    request.statusReason(),
                    now);

            return response(find(agreement.getAgreementId()));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Machine agreement could not be created", exception);
        }
    }

    @Transactional(readOnly = true)
    public List<MachineAgreementResponse> getAll() {
        return agreementRepository.findAllWithDetails()
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public MachineAgreementResponse getById(Long agreementId) {
        return response(find(agreementId));
    }

    public MachineAgreementResponse update(
            Long agreementId,
            MachineAgreementRequest request) {
        MachineAgreement agreement = find(agreementId);
        validateRequest(request, agreementId);

        if (agreement.getAgreementType() != request.agreementType()) {
            throw new IllegalArgumentException(
                    "Agreement type cannot be changed after the agreement is created");
        }

        AgreementStatus previousStatus = agreement.getAgreementStatus();
        AgreementStatus newStatus = request.agreementStatus() == null
                ? previousStatus
                : request.agreementStatus();
        LocalDateTime now = LocalDateTime.now();

        try {
            apply(agreement, request);
            agreement.setAgreementStatus(newStatus);
            if (request.isActive() != null) {
                agreement.setIsActive(request.isActive());
            }
            agreement.setUpdatedBy(request.performedBy());
            agreement.setUpdatedAt(now);
            agreementRepository.saveAndFlush(agreement);

            if (previousStatus != newStatus) {
                addHistory(
                        agreementId,
                        previousStatus,
                        newStatus,
                        request.performedBy(),
                        request.statusReason(),
                        now);
            }

            return response(find(agreementId));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Machine agreement could not be updated", exception);
        }
    }

    public void delete(Long agreementId) {
        MachineAgreement agreement = find(agreementId);

        try {
            historyRepository.deleteAllByAgreementId(agreementId);
            historyRepository.flush();
            agreementRepository.delete(agreement);
            agreementRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Machine agreement " + agreementId
                            + " cannot be deleted because it is referenced by other records",
                    exception);
        }
    }

    private void validateRequest(MachineAgreementRequest request, Long agreementId) {
        requireExists(machineRepository.existsById(request.machineId()),
                "Machine", request.machineId());
        requireExists(userRepository.existsById(request.performedBy()),
                "User", request.performedBy());

        if (!request.agreementEndDate().isAfter(request.agreementStartDate())) {
            throw new IllegalArgumentException(
                    "Agreement end date must be after the agreement start date");
        }

        if (request.installationJobId() != null) {
            requireExists(installationJobRepository.existsById(request.installationJobId()),
                    "Installation job", request.installationJobId());
        }

        if (request.previousAgreementId() != null) {
            if (request.previousAgreementId().equals(agreementId)) {
                throw new IllegalArgumentException(
                        "An agreement cannot reference itself as the previous agreement");
            }

            MachineAgreement previousAgreement = agreementRepository
                    .findById(request.previousAgreementId())
                    .orElseThrow(() -> notFound(
                            "Previous agreement", request.previousAgreementId()));

            if (!previousAgreement.getMachineId().equals(request.machineId())) {
                throw new IllegalArgumentException(
                        "Previous agreement must belong to the same machine");
            }
        }
    }

    private void apply(MachineAgreement agreement, MachineAgreementRequest request) {
        agreement.setMachineId(request.machineId());
        agreement.setAgreementType(request.agreementType());
        agreement.setAgreementStartDate(request.agreementStartDate());
        agreement.setAgreementEndDate(request.agreementEndDate());
        agreement.setAgreementPeriodYears(request.agreementPeriodYears());
        agreement.setVisitsPerYear(request.visitsPerYear());
        agreement.setAnnualPayment(request.annualPayment());
        agreement.setFullPayment(request.fullPayment());
        agreement.setDiscount(request.discount());
        agreement.setVatPercentage(request.vatPercentage());
        agreement.setVatAmount(request.vatAmount());
        agreement.setInstallationJobId(request.installationJobId());
        agreement.setPreviousAgreementId(request.previousAgreementId());
        agreement.setNote(optional(request.note()));
    }

    private void addHistory(
            Long agreementId,
            AgreementStatus previousStatus,
            AgreementStatus newStatus,
            Long changedBy,
            String reason,
            LocalDateTime changedAt) {
        AgreementStatusHistory history = new AgreementStatusHistory();
        history.setAgreementId(agreementId);
        history.setPreviousStatus(previousStatus == null ? null : previousStatus.name());
        history.setNewStatus(newStatus.name());
        history.setChangedAt(changedAt);
        history.setChangedBy(changedBy);
        history.setReason(optional(reason));
        historyRepository.saveAndFlush(history);
    }

    private MachineAgreementResponse response(MachineAgreement agreement) {
        List<AgreementStatusHistoryResponse> history = historyRepository
                .findAllByAgreementIdOrderByChangedAtAsc(agreement.getAgreementId())
                .stream()
                .map(this::historyResponse)
                .toList();

        return new MachineAgreementResponse(
                agreement.getAgreementId(),
                agreement.getAgreementNumber(),
                agreement.getMachineId(),
                agreement.getMachine().getMachineReferenceNumber(),
                agreement.getMachine().getSerialNumber(),
                agreement.getAgreementType(),
                agreement.getAgreementStartDate(),
                agreement.getAgreementEndDate(),
                agreement.getAgreementPeriodYears(),
                agreement.getVisitsPerYear(),
                agreement.getAnnualPayment(),
                agreement.getFullPayment(),
                agreement.getDiscount(),
                agreement.getVatPercentage(),
                agreement.getVatAmount(),
                agreement.getAgreementStatus(),
                agreement.getIsActive(),
                agreement.getInstallationJobId(),
                agreement.getInstallationJob() == null
                        ? null
                        : agreement.getInstallationJob().getJobNumber(),
                agreement.getPreviousAgreementId(),
                agreement.getPreviousAgreement() == null
                        ? null
                        : agreement.getPreviousAgreement().getAgreementNumber(),
                agreement.getNote(),
                agreement.getCreatedBy(),
                agreement.getCreatedAt(),
                agreement.getUpdatedBy(),
                agreement.getUpdatedAt(),
                history);
    }

    private AgreementStatusHistoryResponse historyResponse(
            AgreementStatusHistory history) {
        return new AgreementStatusHistoryResponse(
                history.getAgreementStatusHistoryId(),
                history.getPreviousStatus() == null
                        ? null
                        : AgreementStatus.valueOf(history.getPreviousStatus()),
                AgreementStatus.valueOf(history.getNewStatus()),
                history.getChangedAt(),
                history.getChangedBy(),
                history.getReason());
    }

    private MachineAgreement find(Long agreementId) {
        return agreementRepository.findByIdWithDetails(agreementId)
                .orElseThrow(() -> notFound("Machine agreement", agreementId));
    }

    private String nextAgreementNumber(String prefix) {
        long nextSequence = agreementRepository.findMaximumAgreementSequence(prefix) + 1;
        return prefix + String.format("%06d", nextSequence);
    }

    private void requireExists(boolean exists, String resourceName, Long id) {
        if (!exists) {
            throw notFound(resourceName, id);
        }
    }

    private EntityNotFoundException notFound(String resourceName, Long id) {
        return new EntityNotFoundException(resourceName + " " + id + " was not found");
    }

    private IllegalStateException conflict(String message, Exception exception) {
        return new IllegalStateException(
                message + " because a unique or referenced value is invalid",
                exception);
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
