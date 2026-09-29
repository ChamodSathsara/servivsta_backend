package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.machines.MachineStatusHistoryRequest;
import com.gestetner.servvista.Dto.machines.MachineStatusHistoryResponse;
import com.gestetner.servvista.Service.MachineStatusHistoryService;
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
@RequestMapping("/api/machine-status-history")
@Tag(name = "Machine Status History")
@PreAuthorize("hasRole('ADMIN')")
public class MachineStatusHistoryController {

    private final MachineStatusHistoryService historyService;

    public MachineStatusHistoryController(MachineStatusHistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping
    @Operation(summary = "Create a machine status-history record")
    public ResponseEntity<MachineStatusHistoryResponse> create(
            @Valid @RequestBody MachineStatusHistoryRequest request) {
        MachineStatusHistoryResponse response = historyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all machine status-history records")
    public ResponseEntity<List<MachineStatusHistoryResponse>> getAll() {
        return ResponseEntity.ok(historyService.getAll());
    }

    @GetMapping("/{historyId}")
    @Operation(summary = "Get a machine status-history record by ID")
    public ResponseEntity<MachineStatusHistoryResponse> getById(
            @PathVariable Long historyId) {
        return ResponseEntity.ok(historyService.getById(historyId));
    }

    @GetMapping("/machine/{machineId}")
    @Operation(summary = "Get status history for a machine")
    public ResponseEntity<List<MachineStatusHistoryResponse>> getByMachineId(
            @PathVariable Long machineId) {
        return ResponseEntity.ok(historyService.getByMachineId(machineId));
    }

    @PutMapping("/{historyId}")
    @Operation(summary = "Update a machine status-history record")
    public ResponseEntity<MachineStatusHistoryResponse> update(
            @PathVariable Long historyId,
            @Valid @RequestBody MachineStatusHistoryRequest request) {
        return ResponseEntity.ok(historyService.update(historyId, request));
    }

    @DeleteMapping("/{historyId}")
    @Operation(summary = "Delete a machine status-history record")
    public ResponseEntity<Void> delete(@PathVariable Long historyId) {
        historyService.delete(historyId);
        return ResponseEntity.noContent().build();
    }
}
