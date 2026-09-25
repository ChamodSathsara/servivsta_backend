package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.agreements.MachineAgreementRequest;
import com.gestetner.servvista.Dto.agreements.MachineAgreementResponse;
import com.gestetner.servvista.Service.MachineAgreementService;
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
@RequestMapping("/api/machine-agreements")
@Tag(name = "Machine Agreements")
@PreAuthorize("hasRole('ADMIN')")
public class MachineAgreementController {

    private final MachineAgreementService agreementService;

    public MachineAgreementController(MachineAgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @PostMapping
    @Operation(summary = "Create a machine agreement")
    public ResponseEntity<MachineAgreementResponse> create(
            @Valid @RequestBody MachineAgreementRequest request) {
        MachineAgreementResponse response = agreementService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all machine agreements")
    public ResponseEntity<List<MachineAgreementResponse>> getAll() {
        return ResponseEntity.ok(agreementService.getAll());
    }

    @GetMapping("/{agreementId}")
    @Operation(summary = "Get a machine agreement by ID")
    public ResponseEntity<MachineAgreementResponse> getById(
            @PathVariable Long agreementId) {
        return ResponseEntity.ok(agreementService.getById(agreementId));
    }

    @PutMapping("/{agreementId}")
    @Operation(summary = "Update a machine agreement")
    public ResponseEntity<MachineAgreementResponse> update(
            @PathVariable Long agreementId,
            @Valid @RequestBody MachineAgreementRequest request) {
        return ResponseEntity.ok(agreementService.update(agreementId, request));
    }

    @DeleteMapping("/{agreementId}")
    @Operation(summary = "Delete a machine agreement")
    public ResponseEntity<Void> delete(@PathVariable Long agreementId) {
        agreementService.delete(agreementId);
        return ResponseEntity.noContent().build();
    }
}
