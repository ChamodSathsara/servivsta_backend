package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.breakdowns.*;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownAssignmentStatus;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownReportedByType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import com.gestetner.servvista.Models.entity.breakdowns.BreakdownRecall;
import com.gestetner.servvista.Models.entity.breakdowns.BreakdownTechnicianAssignment;
import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.feedback.FieldServiceFeedback;
import com.gestetner.servvista.Repositories.breakdowns.BreakdownRecallRepository;
import com.gestetner.servvista.Repositories.breakdowns.BreakdownRepository;
import com.gestetner.servvista.Repositories.breakdowns.BreakdownTechnicianAssignmentRepository;
import com.gestetner.servvista.Repositories.customerportal.CustomerPortalAccountRepository;
import com.gestetner.servvista.Repositories.customers.CustomerSiteRepository;
import com.gestetner.servvista.Repositories.feedback.FieldServiceFeedbackRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.services.SolutionTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;
    private final BreakdownTechnicianAssignmentRepository assignmentRepository;
    private final BreakdownRecallRepository recallRepository;
    private final FieldServiceFeedbackRepository feedbackRepository;
    private final MachineRepository machineRepository;
    private final CustomerSiteRepository siteRepository;
    private final CustomerPortalAccountRepository portalAccountRepository;
    private final UserRepository userRepository;
    private final TechnicianRepository technicianRepository;
    private final SolutionTypeRepository solutionTypeRepository;

    public BreakdownService(
            BreakdownRepository breakdownRepository,
            BreakdownTechnicianAssignmentRepository assignmentRepository,
            BreakdownRecallRepository recallRepository,
            FieldServiceFeedbackRepository feedbackRepository,
            MachineRepository machineRepository,
            CustomerSiteRepository siteRepository,
            CustomerPortalAccountRepository portalAccountRepository,
            UserRepository userRepository,
            TechnicianRepository technicianRepository,
            SolutionTypeRepository solutionTypeRepository) {
        this.breakdownRepository = breakdownRepository;
        this.assignmentRepository = assignmentRepository;
        this.recallRepository = recallRepository;
        this.feedbackRepository = feedbackRepository;
        this.machineRepository = machineRepository;
        this.siteRepository = siteRepository;
        this.portalAccountRepository = portalAccountRepository;
        this.userRepository = userRepository;
        this.technicianRepository = technicianRepository;
        this.solutionTypeRepository = solutionTypeRepository;
    }

    public BreakdownResponse create(BreakdownRequest request) {
        validateBreakdownReferences(request);
        Breakdown breakdown = new Breakdown();
        breakdown.setBreakdownNumber(nextBreakdownNumber());
        breakdown.setCreatedAt(LocalDateTime.now());
        applyBreakdown(breakdown, request, true);
        return response(breakdownRepository.saveAndFlush(breakdown));
    }

    @Transactional(readOnly = true)
    public List<BreakdownResponse> getAll() {
        return breakdownRepository.findAllWithDetails().stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public BreakdownResponse getById(Long breakdownId) {
        return response(findBreakdown(breakdownId));
    }

    public BreakdownResponse update(Long breakdownId, BreakdownRequest request) {
        Breakdown breakdown = findBreakdown(breakdownId);
        validateBreakdownReferences(request);
        applyBreakdown(breakdown, request, false);
        return response(breakdownRepository.saveAndFlush(breakdown));
    }

    public void delete(Long breakdownId) {
        Breakdown breakdown = findBreakdown(breakdownId);
        try {
            feedbackRepository.deleteAllByBreakdownId(breakdownId);
            feedbackRepository.flush();
            recallRepository.deleteAllByBreakdownId(breakdownId);
            recallRepository.flush();
            assignmentRepository.deleteAllByBreakdownId(breakdownId);
            assignmentRepository.flush();
            breakdownRepository.delete(breakdown);
            breakdownRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException("Breakdown " + breakdownId
                    + " cannot be deleted because it is referenced by other records", exception);
        }
    }

    public BreakdownAssignmentResponse createAssignment(
            Long breakdownId, BreakdownAssignmentRequest request) {
        Breakdown breakdown = findBreakdown(breakdownId);
        validateAssignmentReferences(request);
        LocalDateTime now = LocalDateTime.now();

        assignmentRepository.findAllByBreakdownIdOrderByAssignedAtDesc(breakdownId).stream()
                .filter(item -> item.getAssignmentStatus() == BreakdownAssignmentStatus.CURRENT)
                .forEach(item -> {
                    item.setAssignmentStatus(BreakdownAssignmentStatus.REASSIGNED);
                    item.setUnassignedAt(now);
                    assignmentRepository.save(item);
                });

        BreakdownTechnicianAssignment assignment = new BreakdownTechnicianAssignment();
        assignment.setBreakdownId(breakdownId);
        assignment.setTechnicianId(request.technicianId());
        assignment.setAssignedAt(now);
        assignment.setAssignedBy(request.assignedBy());
        assignment.setAssignmentStatus(BreakdownAssignmentStatus.CURRENT);
        assignment.setReason(optional(request.reason()));
        assignment = assignmentRepository.saveAndFlush(assignment);

        breakdown.setStatus(BreakdownStatus.ASSIGNED);
        breakdownRepository.saveAndFlush(breakdown);
        return assignmentResponse(assignment);
    }

    @Transactional(readOnly = true)
    public List<BreakdownAssignmentResponse> getAssignments(Long breakdownId) {
        findBreakdown(breakdownId);
        return assignmentRepository.findAllByBreakdownIdOrderByAssignedAtDesc(breakdownId)
                .stream().map(this::assignmentResponse).toList();
    }

    public BreakdownAssignmentResponse updateAssignment(
            Long breakdownId, Long assignmentId, BreakdownAssignmentRequest request) {
        findBreakdown(breakdownId);
        validateAssignmentReferences(request);
        BreakdownTechnicianAssignment assignment = findAssignment(assignmentId);
        ensureBelongsToBreakdown(assignment.getBreakdownId(), breakdownId, "Assignment");
        assignment.setTechnicianId(request.technicianId());
        assignment.setAssignedBy(request.assignedBy());
        BreakdownAssignmentStatus status = request.assignmentStatus() == null
                ? assignment.getAssignmentStatus() : request.assignmentStatus();
        assignment.setAssignmentStatus(status);
        assignment.setUnassignedAt(status == BreakdownAssignmentStatus.REASSIGNED
                ? LocalDateTime.now() : null);
        assignment.setReason(optional(request.reason()));
        return assignmentResponse(assignmentRepository.saveAndFlush(assignment));
    }

    public void deleteAssignment(Long breakdownId, Long assignmentId) {
        findBreakdown(breakdownId);
        BreakdownTechnicianAssignment assignment = findAssignment(assignmentId);
        ensureBelongsToBreakdown(assignment.getBreakdownId(), breakdownId, "Assignment");
        assignmentRepository.delete(assignment);
        assignmentRepository.flush();
    }

    public BreakdownRecallResponse recall(Long breakdownId, BreakdownRecallRequest request) {
        Breakdown breakdown = findBreakdown(breakdownId);
        if (breakdown.getStatus() != BreakdownStatus.COMPLETED) {
            throw new IllegalStateException("Only a completed breakdown can be recalled");
        }
        requireExists(userRepository.existsById(request.recalledBy()), "User", request.recalledBy());

        BreakdownRecall recall = new BreakdownRecall();
        recall.setBreakdownId(breakdownId);
        recall.setRecallNumber(nextRecallNumber());
        recall.setRecallDate(request.recallDate());
        recall.setRecallReason(optional(request.recallReason()));
        recall.setRecalledBy(request.recalledBy());
        recall.setRecalledAt(LocalDateTime.now());
        recall = recallRepository.saveAndFlush(recall);

        breakdown.setStatus(BreakdownStatus.RECALL);
        breakdownRepository.saveAndFlush(breakdown);
        return recallResponse(recall);
    }

    @Transactional(readOnly = true)
    public List<BreakdownRecallResponse> getRecalls(Long breakdownId) {
        findBreakdown(breakdownId);
        return recallRepository.findAllByBreakdownIdOrderByRecalledAtDesc(breakdownId)
                .stream().map(this::recallResponse).toList();
    }

    public BreakdownFeedbackResponse createFeedback(
            Long breakdownId, BreakdownFeedbackRequest request) {
        Breakdown breakdown = validateFeedback(breakdownId, request, null);
        FieldServiceFeedback feedback = new FieldServiceFeedback();
        feedback.setBreakdownId(breakdownId);
        feedback.setRating(request.rating());
        feedback.setComment(optional(request.comment()));
        feedback.setSubmittedByPortalAccountId(request.submittedByPortalAccountId());
        feedback.setCreatedAt(LocalDateTime.now());
        return feedbackResponse(feedbackRepository.saveAndFlush(feedback));
    }

    @Transactional(readOnly = true)
    public BreakdownFeedbackResponse getFeedback(Long breakdownId) {
        findBreakdown(breakdownId);
        return feedbackResponse(feedbackRepository.findByBreakdownId(breakdownId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Feedback for breakdown " + breakdownId + " was not found")));
    }

    public BreakdownFeedbackResponse updateFeedback(
            Long breakdownId, Long feedbackId, BreakdownFeedbackRequest request) {
        validateFeedback(breakdownId, request, feedbackId);
        FieldServiceFeedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Field service feedback " + feedbackId + " was not found"));
        ensureBelongsToBreakdown(feedback.getBreakdownId(), breakdownId, "Feedback");
        feedback.setRating(request.rating());
        feedback.setComment(optional(request.comment()));
        feedback.setSubmittedByPortalAccountId(request.submittedByPortalAccountId());
        return feedbackResponse(feedbackRepository.saveAndFlush(feedback));
    }

    public void deleteFeedback(Long breakdownId, Long feedbackId) {
        findBreakdown(breakdownId);
        FieldServiceFeedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Field service feedback " + feedbackId + " was not found"));
        ensureBelongsToBreakdown(feedback.getBreakdownId(), breakdownId, "Feedback");
        feedbackRepository.delete(feedback);
        feedbackRepository.flush();
    }

    private void validateBreakdownReferences(BreakdownRequest request) {
        var machine = machineRepository.findById(request.machineId())
                .orElseThrow(() -> notFound("Machine", request.machineId()));
        requireExists(siteRepository.existsById(request.customerSiteId()),
                "Customer site", request.customerSiteId());
        if (machine.getCurrentCustomerSiteId() != null
                && !machine.getCurrentCustomerSiteId().equals(request.customerSiteId())) {
            throw new IllegalArgumentException("Customer site does not match the machine's current site");
        }
        validateOptional(request.reportedByPortalAccountId(), portalAccountRepository::existsById,
                "Portal account");
        validateOptional(request.reportedByUserId(), userRepository::existsById, "Reporting user");
        validateOptional(request.informedSolutionTypeId(), solutionTypeRepository::existsById,
                "Informed solution type");
        validateOptional(request.actualSolutionTypeId(), solutionTypeRepository::existsById,
                "Actual solution type");
        validateOptional(request.approvedBy(), userRepository::existsById, "Approving user");
        validateOptional(request.cancelledBy(), userRepository::existsById, "Cancelling user");
        if (request.reportedByType() == BreakdownReportedByType.PORTAL_CONTACT
                && request.reportedByPortalAccountId() == null) {
            throw new IllegalArgumentException(
                    "reportedByPortalAccountId is required for PORTAL_CONTACT reports");
        }
    }

    private void applyBreakdown(Breakdown breakdown, BreakdownRequest request, boolean create) {
        BreakdownStatus oldStatus = breakdown.getStatus();
        BreakdownStatus status = request.status() == null
                ? (create ? BreakdownStatus.PROCESSING : oldStatus) : request.status();
        breakdown.setMachineId(request.machineId());
        breakdown.setCustomerSiteId(request.customerSiteId());
        breakdown.setReportedByType(request.reportedByType());
        breakdown.setReportedByPortalAccountId(request.reportedByPortalAccountId());
        breakdown.setReportedByUserId(request.reportedByUserId());
        breakdown.setReportedNote(optional(request.reportedNote()));
        breakdown.setInformedSolutionTypeId(request.informedSolutionTypeId());
        breakdown.setStatus(status);
        breakdown.setApprovedBy(request.approvedBy());
        breakdown.setStartNote(optional(request.startNote()));
        breakdown.setActualSolutionTypeId(request.actualSolutionTypeId());
        breakdown.setSolutionNote(optional(request.solutionNote()));
        breakdown.setCancelledBy(request.cancelledBy());
        breakdown.setCancelReason(optional(request.cancelReason()));
        breakdown.setExpectedCompletionAt(request.expectedCompletionAt());
        breakdown.setContactEmail(request.contactEmail().trim().toLowerCase(Locale.ROOT));
        LocalDateTime now = LocalDateTime.now();
        if (status == BreakdownStatus.APPROVED && breakdown.getApprovedAt() == null) breakdown.setApprovedAt(now);
        if (status == BreakdownStatus.STARTED && breakdown.getStartedAt() == null) breakdown.setStartedAt(now);
        if (status == BreakdownStatus.COMPLETED && breakdown.getCompletedAt() == null) breakdown.setCompletedAt(now);
        if (status == BreakdownStatus.CANCELLED && breakdown.getCancelledAt() == null) breakdown.setCancelledAt(now);
    }

    private Breakdown validateFeedback(
            Long breakdownId, BreakdownFeedbackRequest request, Long feedbackId) {
        Breakdown breakdown = findBreakdown(breakdownId);
        if (breakdown.getStatus() != BreakdownStatus.COMPLETED
                && breakdown.getStatus() != BreakdownStatus.RECALL) {
            throw new IllegalStateException(
                    "Feedback can only be submitted for a completed or recalled breakdown");
        }
        CustomerPortalAccount account = portalAccountRepository
                .findById(request.submittedByPortalAccountId())
                .orElseThrow(() -> notFound("Portal account", request.submittedByPortalAccountId()));
        if (!account.getSiteContact().getCustomerSiteId().equals(breakdown.getCustomerSiteId())) {
            throw new IllegalArgumentException(
                    "Portal account does not belong to the breakdown customer site");
        }
        boolean exists = feedbackId == null
                ? feedbackRepository.existsByBreakdownId(breakdownId)
                : feedbackRepository.existsByBreakdownIdAndFeedbackIdNot(breakdownId, feedbackId);
        if (exists) throw new IllegalStateException("Feedback already exists for breakdown " + breakdownId);
        return breakdown;
    }

    private void validateAssignmentReferences(BreakdownAssignmentRequest request) {
        requireExists(technicianRepository.existsById(request.technicianId()),
                "Technician", request.technicianId());
        validateOptional(request.assignedBy(), userRepository::existsById, "Assigning user");
    }

    private Breakdown findBreakdown(Long id) {
        return breakdownRepository.findByIdWithDetails(id)
                .orElseThrow(() -> notFound("Breakdown", id));
    }

    private BreakdownTechnicianAssignment findAssignment(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> notFound("Breakdown technician assignment", id));
    }

    private BreakdownResponse response(Breakdown b) {
        return new BreakdownResponse(b.getBreakdownId(), b.getBreakdownNumber(), b.getMachineId(),
                b.getMachine().getMachineReferenceNumber(), b.getCustomerSiteId(),
                b.getCustomerSite().getSiteName(), b.getReportedByType(),
                b.getReportedByPortalAccountId(), b.getReportedByUserId(), b.getReportedNote(),
                b.getInformedSolutionTypeId(), b.getStatus(), b.getApprovedBy(), b.getApprovedAt(),
                b.getStartNote(), b.getStartedAt(), b.getActualSolutionTypeId(), b.getSolutionNote(),
                b.getCompletedAt(), b.getCancelledBy(), b.getCancelledAt(), b.getCancelReason(),
                b.getCreatedAt(), b.getExpectedCompletionAt(), b.getContactEmail());
    }

    private BreakdownAssignmentResponse assignmentResponse(BreakdownTechnicianAssignment a) {
        return new BreakdownAssignmentResponse(a.getBreakdownTechnicianAssignmentId(), a.getBreakdownId(),
                a.getTechnicianId(), a.getAssignedAt(), a.getAssignedBy(), a.getUnassignedAt(),
                a.getAssignmentStatus(), a.getReason());
    }

    private BreakdownRecallResponse recallResponse(BreakdownRecall r) {
        return new BreakdownRecallResponse(r.getBreakdownRecallId(), r.getBreakdownId(),
                r.getRecallNumber(), r.getRecallDate(), r.getRecallReason(), r.getRecalledBy(),
                r.getRecalledAt());
    }

    private BreakdownFeedbackResponse feedbackResponse(FieldServiceFeedback f) {
        return new BreakdownFeedbackResponse(f.getFeedbackId(), f.getBreakdownId(), f.getRating(),
                f.getComment(), f.getSubmittedByPortalAccountId(), f.getCreatedAt());
    }

    private String nextBreakdownNumber() {
        return "BD" + String.format("%06d", breakdownRepository.findMaximumBreakdownSequence() + 1);
    }

    private String nextRecallNumber() {
        return "RC" + String.format("%06d", recallRepository.findMaximumRecallSequence() + 1);
    }

    private void ensureBelongsToBreakdown(Long actual, Long expected, String resource) {
        if (!actual.equals(expected)) throw new IllegalArgumentException(resource + " does not belong to breakdown " + expected);
    }

    private <T> void validateOptional(Long id, java.util.function.Predicate<Long> exists, String name) {
        if (id != null) requireExists(exists.test(id), name, id);
    }

    private void requireExists(boolean exists, String name, Long id) {
        if (!exists) throw notFound(name, id);
    }

    private EntityNotFoundException notFound(String name, Long id) {
        return new EntityNotFoundException(name + " " + id + " was not found");
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
