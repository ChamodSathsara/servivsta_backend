package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.breakdowns.*;
import com.gestetner.servvista.Service.BreakdownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breakdowns")
@Tag(name = "Breakdowns")
@PreAuthorize("hasRole('ADMIN')")
public class BreakdownController {

    private final BreakdownService breakdownService;

    public BreakdownController(BreakdownService breakdownService) {
        this.breakdownService = breakdownService;
    }

    @PostMapping
    @Operation(summary = "Create a breakdown")
    public ResponseEntity<BreakdownResponse> create(
            @Valid @RequestBody BreakdownRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(breakdownService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all breakdowns")
    public ResponseEntity<List<BreakdownResponse>> getAll() {
        return ResponseEntity.ok(breakdownService.getAll());
    }

    @GetMapping("/{breakdownId}")
    @Operation(summary = "Get a breakdown by ID")
    public ResponseEntity<BreakdownResponse> getById(@PathVariable Long breakdownId) {
        return ResponseEntity.ok(breakdownService.getById(breakdownId));
    }

    @PutMapping("/{breakdownId}")
    @Operation(summary = "Update a breakdown")
    public ResponseEntity<BreakdownResponse> update(
            @PathVariable Long breakdownId,
            @Valid @RequestBody BreakdownRequest request) {
        return ResponseEntity.ok(breakdownService.update(breakdownId, request));
    }

    @DeleteMapping("/{breakdownId}")
    @Operation(summary = "Delete a breakdown")
    public ResponseEntity<Void> delete(@PathVariable Long breakdownId) {
        breakdownService.delete(breakdownId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{breakdownId}/assignments")
    @Operation(summary = "Assign a technician to a breakdown")
    public ResponseEntity<BreakdownAssignmentResponse> createAssignment(
            @PathVariable Long breakdownId,
            @Valid @RequestBody BreakdownAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(breakdownService.createAssignment(breakdownId, request));
    }

    @GetMapping("/{breakdownId}/assignments")
    @Operation(summary = "Get technician assignments for a breakdown")
    public ResponseEntity<List<BreakdownAssignmentResponse>> getAssignments(
            @PathVariable Long breakdownId) {
        return ResponseEntity.ok(breakdownService.getAssignments(breakdownId));
    }

    @PutMapping("/{breakdownId}/assignments/{assignmentId}")
    @Operation(summary = "Update a breakdown technician assignment")
    public ResponseEntity<BreakdownAssignmentResponse> updateAssignment(
            @PathVariable Long breakdownId,
            @PathVariable Long assignmentId,
            @Valid @RequestBody BreakdownAssignmentRequest request) {
        return ResponseEntity.ok(
                breakdownService.updateAssignment(breakdownId, assignmentId, request));
    }

    @DeleteMapping("/{breakdownId}/assignments/{assignmentId}")
    @Operation(summary = "Delete a breakdown technician assignment")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long breakdownId,
            @PathVariable Long assignmentId) {
        breakdownService.deleteAssignment(breakdownId, assignmentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{breakdownId}/recalls")
    @Operation(summary = "Manually recall a completed breakdown")
    public ResponseEntity<BreakdownRecallResponse> recall(
            @PathVariable Long breakdownId,
            @Valid @RequestBody BreakdownRecallRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(breakdownService.recall(breakdownId, request));
    }

    @GetMapping("/{breakdownId}/recalls")
    @Operation(summary = "Get recalls for a breakdown")
    public ResponseEntity<List<BreakdownRecallResponse>> getRecalls(
            @PathVariable Long breakdownId) {
        return ResponseEntity.ok(breakdownService.getRecalls(breakdownId));
    }

    @PostMapping("/{breakdownId}/feedback")
    @Operation(summary = "Add customer feedback to a breakdown")
    public ResponseEntity<BreakdownFeedbackResponse> createFeedback(
            @PathVariable Long breakdownId,
            @Valid @RequestBody BreakdownFeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(breakdownService.createFeedback(breakdownId, request));
    }

    @GetMapping("/{breakdownId}/feedback")
    @Operation(summary = "Get customer feedback for a breakdown")
    public ResponseEntity<BreakdownFeedbackResponse> getFeedback(
            @PathVariable Long breakdownId) {
        return ResponseEntity.ok(breakdownService.getFeedback(breakdownId));
    }

    @PutMapping("/{breakdownId}/feedback/{feedbackId}")
    @Operation(summary = "Update customer feedback for a breakdown")
    public ResponseEntity<BreakdownFeedbackResponse> updateFeedback(
            @PathVariable Long breakdownId,
            @PathVariable Long feedbackId,
            @Valid @RequestBody BreakdownFeedbackRequest request) {
        return ResponseEntity.ok(
                breakdownService.updateFeedback(breakdownId, feedbackId, request));
    }

    @DeleteMapping("/{breakdownId}/feedback/{feedbackId}")
    @Operation(summary = "Delete customer feedback for a breakdown")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long breakdownId,
            @PathVariable Long feedbackId) {
        breakdownService.deleteFeedback(breakdownId, feedbackId);
        return ResponseEntity.noContent().build();
    }
}
