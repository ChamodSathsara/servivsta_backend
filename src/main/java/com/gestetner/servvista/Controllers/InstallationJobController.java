package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.installations.InstallationJobRequest;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
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
@RequestMapping("/api/installation-jobs")
@Tag(name = "Installation Jobs")
@PreAuthorize("hasRole('ADMIN')")
public class InstallationJobController {

    private final InstallationService installationService;

    public InstallationJobController(InstallationService installationService) {
        this.installationService = installationService;
    }

    @PostMapping
    @Operation(summary = "Create an installation job")
    public ResponseEntity<InstallationJobResponse> create(
            @Valid @RequestBody InstallationJobRequest request) {
        InstallationJobResponse response = installationService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all installation jobs")
    public ResponseEntity<List<InstallationJobResponse>> getAll() {
        return ResponseEntity.ok(installationService.getAllJobs());
    }

    @GetMapping("/{jobId}")
    @Operation(summary = "Get an installation job by ID")
    public ResponseEntity<InstallationJobResponse> getById(@PathVariable Long jobId) {
        return ResponseEntity.ok(installationService.getJobById(jobId));
    }

    @PutMapping("/{jobId}")
    @Operation(summary = "Update an installation job")
    public ResponseEntity<InstallationJobResponse> update(
            @PathVariable Long jobId,
            @Valid @RequestBody InstallationJobRequest request) {
        return ResponseEntity.ok(installationService.updateJob(jobId, request));
    }
}
