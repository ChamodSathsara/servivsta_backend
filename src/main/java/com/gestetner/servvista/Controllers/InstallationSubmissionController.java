package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.installations.InstallationSubmissionRequest;
import com.gestetner.servvista.Dto.installations.InstallationSubmissionResponse;
import com.gestetner.servvista.Service.InstallationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/installation-submissions")
@Tag(name = "Installation Submissions")
@PreAuthorize("hasRole('ADMIN')")
public class InstallationSubmissionController {

    private final InstallationService installationService;

    public InstallationSubmissionController(InstallationService installationService) {
        this.installationService = installationService;
    }

    @PostMapping
    @Operation(summary = "Create an installation submission")
    public ResponseEntity<InstallationSubmissionResponse> create(
            @Valid @RequestBody InstallationSubmissionRequest request) {
        InstallationSubmissionResponse response = installationService.createSubmission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all installation submissions")
    public ResponseEntity<List<InstallationSubmissionResponse>> getAll() {
        return ResponseEntity.ok(installationService.getAllSubmissions());
    }

    @GetMapping("/{submissionId}")
    @Operation(summary = "Get an installation submission by ID")
    public ResponseEntity<InstallationSubmissionResponse> getById(
            @PathVariable Long submissionId) {
        return ResponseEntity.ok(installationService.getSubmissionById(submissionId));
    }

    @PutMapping("/{submissionId}")
    @Operation(summary = "Update an installation submission")
    public ResponseEntity<InstallationSubmissionResponse> update(
            @PathVariable Long submissionId,
            @Valid @RequestBody InstallationSubmissionRequest request) {
        return ResponseEntity.ok(
                installationService.updateSubmission(submissionId, request));
    }
}
