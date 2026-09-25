package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.catalog.MachineModelRequest;
import com.gestetner.servvista.Dto.catalog.MachineModelResponse;
import com.gestetner.servvista.Service.MachineModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/machine-models") @Tag(name = "Machine Models") @PreAuthorize("hasRole('ADMIN')")
public class MachineModelController {
    private final MachineModelService service;

    public MachineModelController(MachineModelService service) {
        this.service = service;
    }

    @PostMapping @Operation(summary = "Create a machine model")
    public ResponseEntity<MachineModelResponse> create(@Valid @RequestBody MachineModelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping @Operation(summary = "Get all machine models")
    public List<MachineModelResponse> getAll() { return service.getAll();
    }

    @PutMapping("/{modelId}") @Operation(summary = "Update a machine model")
    public MachineModelResponse update(@PathVariable Long modelId, @Valid @RequestBody MachineModelRequest request) {
        return service.update(modelId, request);
    }

    @DeleteMapping("/{modelId}") @Operation(summary = "Delete a machine model")
    public ResponseEntity<Void> delete(@PathVariable Long modelId) {
        service.delete(modelId);
        return ResponseEntity.noContent().build();
    }
}
