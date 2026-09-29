package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.reference.SolutionTypeRequest;
import com.gestetner.servvista.Dto.reference.SolutionTypeResponse;
import com.gestetner.servvista.Service.SolutionTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solution-types")
@Tag(name = "Solution Types")
@PreAuthorize("hasRole('ADMIN')")
public class SolutionTypeController {

    private final SolutionTypeService service;

    public SolutionTypeController(SolutionTypeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a solution type")
    public ResponseEntity<SolutionTypeResponse> create(
            @Valid @RequestBody SolutionTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all solution types")
    public ResponseEntity<List<SolutionTypeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{solutionTypeId}")
    @Operation(summary = "Get a solution type by ID")
    public ResponseEntity<SolutionTypeResponse> getById(@PathVariable Long solutionTypeId) {
        return ResponseEntity.ok(service.getById(solutionTypeId));
    }

    @PutMapping("/{solutionTypeId}")
    @Operation(summary = "Update a solution type")
    public ResponseEntity<SolutionTypeResponse> update(
            @PathVariable Long solutionTypeId,
            @Valid @RequestBody SolutionTypeRequest request) {
        return ResponseEntity.ok(service.update(solutionTypeId, request));
    }

    @DeleteMapping("/{solutionTypeId}")
    @Operation(summary = "Delete a solution type")
    public ResponseEntity<Void> delete(@PathVariable Long solutionTypeId) {
        service.delete(solutionTypeId);
        return ResponseEntity.noContent().build();
    }
}
