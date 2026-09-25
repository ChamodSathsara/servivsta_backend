package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.catalog.MachineTypeRequest;
import com.gestetner.servvista.Dto.catalog.MachineTypeResponse;
import com.gestetner.servvista.Service.MachineTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/machine-types") @Tag(name = "Machine Types") @PreAuthorize("hasRole('ADMIN')")
public class MachineTypeController {

    private final MachineTypeService service;

    public MachineTypeController(MachineTypeService service) {
        this.service = service;
    }

    @PostMapping @Operation(summary = "Create a machine type")
    public ResponseEntity<MachineTypeResponse> create(@Valid @RequestBody MachineTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping @Operation(summary = "Get all machine types")
    public List<MachineTypeResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{machineTypeId}") @Operation(summary = "Update a machine type")
    public MachineTypeResponse update(@PathVariable Long machineTypeId, @Valid @RequestBody MachineTypeRequest request) {
        return service.update(machineTypeId, request);
    }

    @DeleteMapping("/{machineTypeId}") @Operation(summary = "Delete a machine type")
    public ResponseEntity<Void> delete(@PathVariable Long machineTypeId) {
        service.delete(machineTypeId);
        return ResponseEntity.noContent().build();
    }
}
