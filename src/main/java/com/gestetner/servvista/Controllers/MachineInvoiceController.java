package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.machines.MachineInvoiceRequest;
import com.gestetner.servvista.Dto.machines.MachineInvoiceResponse;
import com.gestetner.servvista.Service.MachineInvoiceService;
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
@RequestMapping("/api/machine-invoices")
@Tag(name = "Machine Invoices")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
public class MachineInvoiceController {

    private final MachineInvoiceService machineInvoiceService;

    public MachineInvoiceController(MachineInvoiceService machineInvoiceService) {
        this.machineInvoiceService = machineInvoiceService;
    }

    @PostMapping
    @Operation(summary = "Create a machine invoice")
    public ResponseEntity<MachineInvoiceResponse> create(
            @Valid @RequestBody MachineInvoiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(machineInvoiceService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all machine invoices")
    public ResponseEntity<List<MachineInvoiceResponse>> getAll() {
        return ResponseEntity.ok(machineInvoiceService.getAll());
    }

    @GetMapping("/{machineInvoiceId}")
    @Operation(summary = "Get a machine invoice by ID")
    public ResponseEntity<MachineInvoiceResponse> getById(
            @PathVariable Long machineInvoiceId) {
        return ResponseEntity.ok(machineInvoiceService.getById(machineInvoiceId));
    }

    @PutMapping("/{machineInvoiceId}")
    @Operation(summary = "Update a machine invoice")
    public ResponseEntity<MachineInvoiceResponse> update(
            @PathVariable Long machineInvoiceId,
            @Valid @RequestBody MachineInvoiceRequest request) {
        return ResponseEntity.ok(machineInvoiceService.update(machineInvoiceId, request));
    }

    @DeleteMapping("/{machineInvoiceId}")
    @Operation(summary = "Delete a machine invoice")
    public ResponseEntity<Void> delete(@PathVariable Long machineInvoiceId) {
        machineInvoiceService.delete(machineInvoiceId);
        return ResponseEntity.noContent().build();
    }
}
