package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.installations.InstallationJobRequest;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
import com.gestetner.servvista.Dto.installations.InstallationStatusHistoryResponse;
import com.gestetner.servvista.Dto.installations.InstallationSubmissionRequest;
import com.gestetner.servvista.Dto.installations.InstallationSubmissionResponse;
import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Installations.InstallationVerificationStatus;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.customers.SiteContact;
import com.gestetner.servvista.Models.entity.installations.InstallationJob;
import com.gestetner.servvista.Models.entity.installations.InstallationStatusHistory;
import com.gestetner.servvista.Models.entity.installations.InstallationSubmission;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import com.gestetner.servvista.Models.entity.machines.MachineModel;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import com.gestetner.servvista.Repositories.customers.CustomerSiteRepository;
import com.gestetner.servvista.Repositories.customers.SiteContactRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.installations.InstallationJobRepository;
import com.gestetner.servvista.Repositories.installations.InstallationStatusHistoryRepository;
import com.gestetner.servvista.Repositories.installations.InstallationSubmissionRepository;
import com.gestetner.servvista.Repositories.machines.MachineInvoiceRepository;
import com.gestetner.servvista.Repositories.machines.MachineModelRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.sales.DealerRepository;
import com.gestetner.servvista.Repositories.sales.RepRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class InstallationService {

    private final InstallationJobRepository jobRepository;
    private final InstallationSubmissionRepository submissionRepository;
    private final InstallationStatusHistoryRepository historyRepository;
    private final CustomerRepository customerRepository;
    private final CustomerSiteRepository siteRepository;
    private final SiteContactRepository contactRepository;
    private final MachineRepository machineRepository;
    private final MachineModelRepository modelRepository;
    private final MachineInvoiceRepository invoiceRepository;
    private final TechnicianRepository technicianRepository;
    private final DealerRepository dealerRepository;
    private final RepRepository repRepository;
    private final UserRepository userRepository;

    public InstallationService(
            InstallationJobRepository jobRepository,
            InstallationSubmissionRepository submissionRepository,
            InstallationStatusHistoryRepository historyRepository,
            CustomerRepository customerRepository,
            CustomerSiteRepository siteRepository,
            SiteContactRepository contactRepository,
            MachineRepository machineRepository,
            MachineModelRepository modelRepository,
            MachineInvoiceRepository invoiceRepository,
            TechnicianRepository technicianRepository,
            DealerRepository dealerRepository,
            RepRepository repRepository,
            UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.submissionRepository = submissionRepository;
        this.historyRepository = historyRepository;
        this.customerRepository = customerRepository;
        this.siteRepository = siteRepository;
        this.contactRepository = contactRepository;
        this.machineRepository = machineRepository;
        this.modelRepository = modelRepository;
        this.invoiceRepository = invoiceRepository;
        this.technicianRepository = technicianRepository;
        this.dealerRepository = dealerRepository;
        this.repRepository = repRepository;
        this.userRepository = userRepository;
    }

    public InstallationJobResponse createJob(InstallationJobRequest request) {
        validateJobReferences(request);
        InstallationJobStatus status = request.status() == null
                ? InstallationJobStatus.ASSIGNED
                : request.status();
        LocalDateTime now = LocalDateTime.now();

        try {
            InstallationJob job = new InstallationJob();
            job.setJobNumber(nextJobNumber());
            applyJob(job, request);
            job.setStatus(status);
            job.setCreatedBy(request.performedBy());
            job.setCreatedAt(now);
            job = jobRepository.saveAndFlush(job);

            addHistory(job.getInstallationJobId(), null, status,
                    request.performedBy(), request.statusNote(), now);
            return jobResponse(findJob(job.getInstallationJobId()));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Installation job could not be created", exception);
        }
    }

    @Transactional(readOnly = true)
    public List<InstallationJobResponse> getAllJobs() {
        return jobRepository.findAllWithDetails()
                .stream()
                .map(this::jobResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public InstallationJobResponse getJobById(Long jobId) {
        return jobResponse(findJob(jobId));
    }

    public InstallationJobResponse updateJob(Long jobId, InstallationJobRequest request) {
        InstallationJob job = findJob(jobId);
        validateJobReferences(request);
        InstallationJobStatus previousStatus = job.getStatus();
        InstallationJobStatus newStatus = request.status() == null
                ? previousStatus
                : request.status();

        try {
            applyJob(job, request);
            job.setStatus(newStatus);
            jobRepository.saveAndFlush(job);
            recordStatusChange(job, previousStatus, newStatus,
                    request.performedBy(), request.statusNote());
            return jobResponse(findJob(jobId));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Installation job could not be updated", exception);
        }
    }

    public InstallationSubmissionResponse createSubmission(InstallationSubmissionRequest request) {
        if (submissionRepository.existsByInstallationJobId(request.installationJobId())) {
            throw new IllegalStateException(
                    "An installation submission already exists for job " + request.installationJobId());
        }

        SubmissionReferences references = validateSubmissionReferences(request);
        validateVerification(request);
        LocalDateTime now = LocalDateTime.now();

        try {
            InstallationSubmission submission = new InstallationSubmission();
            applySubmission(submission, request, true, now);
            submission = submissionRepository.saveAndFlush(submission);

            InstallationJob job = references.job();
            if (job.getCustomerSiteId() == null) {
                job.setCustomerSiteId(request.customerSiteId());
            }
            changeJobStatus(job, InstallationJobStatus.SUBMITTED,
                    request.submittedBy(), request.statusNote(), now);

            return submissionResponse(
                    findSubmission(submission.getInstallationSubmissionId()));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Installation submission could not be created", exception);
        }
    }

    @Transactional(readOnly = true)
    public List<InstallationSubmissionResponse> getAllSubmissions() {
        return submissionRepository.findAllWithDetails()
                .stream()
                .map(this::submissionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public InstallationSubmissionResponse getSubmissionById(Long submissionId) {
        return submissionResponse(findSubmission(submissionId));
    }

    public InstallationSubmissionResponse updateSubmission(
            Long submissionId,
            InstallationSubmissionRequest request) {
        InstallationSubmission submission = findSubmission(submissionId);
        if (!submission.getInstallationJobId().equals(request.installationJobId())) {
            throw new IllegalArgumentException(
                    "The installation job of a submission cannot be changed");
        }

        SubmissionReferences references = validateSubmissionReferences(request);
        validateVerification(request);
        LocalDateTime now = LocalDateTime.now();

        try {
            applySubmission(submission, request, false, now);
            submissionRepository.saveAndFlush(submission);

            InstallationJobStatus targetStatus = statusForVerification(
                    submission.getVerificationStatus());
            if (targetStatus != null) {
                Long changedBy = request.verifiedBy() == null
                        ? request.submittedBy()
                        : request.verifiedBy();
                changeJobStatus(references.job(), targetStatus,
                        changedBy, request.statusNote(), now);
            }

            return submissionResponse(findSubmission(submissionId));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Installation submission could not be updated", exception);
        }
    }

    private void validateJobReferences(InstallationJobRequest request) {
        requireExists(customerRepository.existsById(request.customerId()),
                "Customer", request.customerId());
        requireExists(technicianRepository.existsById(request.assignedTechnicianId()),
                "Technician", request.assignedTechnicianId());
        requireExists(userRepository.existsById(request.performedBy()),
                "User", request.performedBy());

        if (request.customerSiteId() != null) {
            CustomerSite site = siteRepository.findById(request.customerSiteId())
                    .orElseThrow(() -> notFound("Customer site", request.customerSiteId()));
            if (!site.getCustomerId().equals(request.customerId())) {
                throw new IllegalArgumentException(
                        "Customer site does not belong to the selected customer");
            }
        }

        if (request.machineInvoiceId() != null) {
            MachineInvoice invoice = invoiceRepository.findById(request.machineInvoiceId())
                    .orElseThrow(() -> notFound("Machine invoice", request.machineInvoiceId()));
            if (!invoice.getCustomerId().equals(request.customerId())) {
                throw new IllegalArgumentException(
                        "Machine invoice does not belong to the selected customer");
            }
        }

        validateOptional(request.dealerId(), dealerRepository::existsById, "Dealer");
        validateOptional(request.repId(), repRepository::existsById, "Rep");
    }

    private SubmissionReferences validateSubmissionReferences(
            InstallationSubmissionRequest request) {
        InstallationJob job = findJob(request.installationJobId());
        Machine machine = machineRepository.findById(request.machineId())
                .orElseThrow(() -> notFound("Machine", request.machineId()));
        MachineModel model = modelRepository.findById(request.modelId())
                .orElseThrow(() -> notFound("Machine model", request.modelId()));
        CustomerSite site = siteRepository.findById(request.customerSiteId())
                .orElseThrow(() -> notFound("Customer site", request.customerSiteId()));
        SiteContact contact = contactRepository.findById(request.siteContactId())
                .orElseThrow(() -> notFound("Site contact", request.siteContactId()));
        requireExists(userRepository.existsById(request.submittedBy()),
                "Submitting user", request.submittedBy());

        if (!machine.getModelId().equals(model.getModelId())) {
            throw new IllegalArgumentException(
                    "Selected model does not belong to the selected machine");
        }
        if (!site.getCustomerId().equals(job.getCustomerId())) {
            throw new IllegalArgumentException(
                    "Customer site does not belong to the installation job customer");
        }
        if (!contact.getCustomerSiteId().equals(site.getCustomerSiteId())) {
            throw new IllegalArgumentException(
                    "Site contact does not belong to the selected customer site");
        }
        if (job.getCustomerSiteId() != null
                && !job.getCustomerSiteId().equals(site.getCustomerSiteId())) {
            throw new IllegalArgumentException(
                    "Submission site does not match the installation job site");
        }
        if (request.verifiedBy() != null) {
            requireExists(userRepository.existsById(request.verifiedBy()),
                    "Verifying user", request.verifiedBy());
        }

        return new SubmissionReferences(job);
    }

    private void validateVerification(InstallationSubmissionRequest request) {
        InstallationVerificationStatus status = request.verificationStatus() == null
                ? InstallationVerificationStatus.PENDING_VERIFICATION
                : request.verificationStatus();

        if (status != InstallationVerificationStatus.PENDING_VERIFICATION
                && request.verifiedBy() == null) {
            throw new IllegalArgumentException(
                    "verifiedBy is required when a submission is verified or rejected");
        }
    }

    private void applyJob(InstallationJob job, InstallationJobRequest request) {
        job.setCompany(request.company());
        job.setDivision(request.division());
        job.setCustomerId(request.customerId());
        job.setCustomerSiteId(request.customerSiteId());
        job.setMachineInvoiceId(request.machineInvoiceId());
        job.setDealerId(request.dealerId());
        job.setRepId(request.repId());
        job.setAssignedTechnicianId(request.assignedTechnicianId());
        job.setExpectedInstallDate(request.expectedInstallDate());
    }

    private void applySubmission(
            InstallationSubmission submission,
            InstallationSubmissionRequest request,
            boolean create,
            LocalDateTime now) {
        submission.setInstallationJobId(request.installationJobId());
        submission.setMachineId(request.machineId());
        submission.setModelId(request.modelId());
        submission.setCustomerSiteId(request.customerSiteId());
        submission.setSiteContactId(request.siteContactId());
        submission.setInstallDate(request.installDate());
        submission.setInitialMeterReading(request.initialMeterReading());
        submission.setAgreementTypeRequested(request.agreementTypeRequested());
        submission.setWarrantyNote(optional(request.warrantyNote()));

        if (create) {
            submission.setSubmittedBy(request.submittedBy());
            submission.setSubmittedAt(now);
        }

        InstallationVerificationStatus verificationStatus =
                request.verificationStatus() == null
                        ? InstallationVerificationStatus.PENDING_VERIFICATION
                        : request.verificationStatus();
        submission.setVerificationStatus(verificationStatus);
        submission.setVerificationNote(optional(request.verificationNote()));

        if (verificationStatus == InstallationVerificationStatus.PENDING_VERIFICATION) {
            submission.setVerifiedBy(null);
            submission.setVerifiedAt(null);
        } else {
            submission.setVerifiedBy(request.verifiedBy());
            submission.setVerifiedAt(now);
        }
    }

    private void changeJobStatus(
            InstallationJob job,
            InstallationJobStatus newStatus,
            Long changedBy,
            String note,
            LocalDateTime changedAt) {
        InstallationJobStatus previousStatus = job.getStatus();
        if (previousStatus == newStatus) {
            return;
        }

        job.setStatus(newStatus);
        jobRepository.saveAndFlush(job);
        addHistory(job.getInstallationJobId(), previousStatus, newStatus,
                changedBy, note, changedAt);
    }

    private void recordStatusChange(
            InstallationJob job,
            InstallationJobStatus previousStatus,
            InstallationJobStatus newStatus,
            Long changedBy,
            String note) {
        if (previousStatus != newStatus) {
            addHistory(job.getInstallationJobId(), previousStatus, newStatus,
                    changedBy, note, LocalDateTime.now());
        }
    }

    private void addHistory(
            Long jobId,
            InstallationJobStatus previousStatus,
            InstallationJobStatus newStatus,
            Long changedBy,
            String note,
            LocalDateTime changedAt) {
        InstallationStatusHistory history = new InstallationStatusHistory();
        history.setInstallationJobId(jobId);
        history.setPreviousStatus(previousStatus == null ? null : previousStatus.name());
        history.setNewStatus(newStatus.name());
        history.setChangedAt(changedAt);
        history.setChangedBy(changedBy);
        history.setNote(optional(note));
        historyRepository.saveAndFlush(history);
    }

    private InstallationJobResponse jobResponse(InstallationJob job) {
        List<InstallationStatusHistoryResponse> history = historyRepository
                .findAllByInstallationJobIdOrderByChangedAtAsc(job.getInstallationJobId())
                .stream()
                .map(this::historyResponse)
                .toList();

        return new InstallationJobResponse(
                job.getInstallationJobId(),
                job.getJobNumber(),
                job.getCompany(),
                job.getDivision(),
                job.getCustomerId(),
                job.getCustomer().getCustomerName(),
                job.getCustomerSiteId(),
                job.getCustomerSite() == null ? null : job.getCustomerSite().getSiteName(),
                job.getMachineInvoiceId(),
                job.getMachineInvoice() == null ? null : job.getMachineInvoice().getInvoiceNumber(),
                job.getDealerId(),
                job.getRepId(),
                job.getAssignedTechnicianId(),
                job.getAssignedTechnician().getTechnicianName(),
                job.getExpectedInstallDate(),
                job.getStatus(),
                job.getCreatedBy(),
                job.getCreatedAt(),
                history);
    }

    private InstallationStatusHistoryResponse historyResponse(
            InstallationStatusHistory history) {
        return new InstallationStatusHistoryResponse(
                history.getInstallationStatusHistoryId(),
                history.getPreviousStatus() == null
                        ? null
                        : InstallationJobStatus.valueOf(history.getPreviousStatus()),
                InstallationJobStatus.valueOf(history.getNewStatus()),
                history.getChangedAt(),
                history.getChangedBy(),
                history.getNote());
    }

    private InstallationSubmissionResponse submissionResponse(
            InstallationSubmission submission) {
        return new InstallationSubmissionResponse(
                submission.getInstallationSubmissionId(),
                submission.getInstallationJobId(),
                submission.getInstallationJob().getJobNumber(),
                submission.getInstallationJob().getStatus(),
                submission.getMachineId(),
                submission.getMachine().getMachineReferenceNumber(),
                submission.getModelId(),
                submission.getModel().getModelNumber(),
                submission.getCustomerSiteId(),
                submission.getCustomerSite().getSiteName(),
                submission.getSiteContactId(),
                submission.getSiteContact().getContactName(),
                submission.getInstallDate(),
                submission.getInitialMeterReading(),
                submission.getAgreementTypeRequested(),
                submission.getWarrantyNote(),
                submission.getSubmittedBy(),
                submission.getSubmittedAt(),
                submission.getVerificationStatus(),
                submission.getVerifiedBy(),
                submission.getVerifiedAt(),
                submission.getVerificationNote());
    }

    private InstallationJob findJob(Long jobId) {
        return jobRepository.findByIdWithDetails(jobId)
                .orElseThrow(() -> notFound("Installation job", jobId));
    }

    private InstallationSubmission findSubmission(Long submissionId) {
        return submissionRepository.findByIdWithDetails(submissionId)
                .orElseThrow(() -> notFound("Installation submission", submissionId));
    }

    private InstallationJobStatus statusForVerification(
            InstallationVerificationStatus verificationStatus) {
        return switch (verificationStatus) {
            case VERIFIED -> InstallationJobStatus.VERIFIED;
            case REJECTED -> InstallationJobStatus.REJECTED;
            case PENDING_VERIFICATION -> null;
        };
    }

    private String nextJobNumber() {
        return "IJ" + String.format("%06d", jobRepository.findMaximumJobNumberSequence() + 1);
    }

    private void validateOptional(
            Long id,
            java.util.function.Predicate<Long> exists,
            String resourceName) {
        if (id != null) {
            requireExists(exists.test(id), resourceName, id);
        }
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

    private record SubmissionReferences(InstallationJob job) {
    }
}
