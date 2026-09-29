package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.reference.MeterCounterTypeRequest;
import com.gestetner.servvista.Dto.reference.MeterCounterTypeResponse;
import com.gestetner.servvista.Service.MeterCounterTypeService;
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
@RequestMapping("/api/meter-counter-types")
@Tag(name = "Meter Counter Types")
@PreAuthorize("hasRole('ADMIN')")
public class MeterCounterTypeController {

    private final MeterCounterTypeService service;

    public MeterCounterTypeController(MeterCounterTypeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a meter counter type")
    public ResponseEntity<MeterCounterTypeResponse> create(
            @Valid @RequestBody MeterCounterTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all meter counter types")
    public ResponseEntity<List<MeterCounterTypeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{meterCounterTypeId}")
    @Operation(summary = "Get a meter counter type by ID")
    public ResponseEntity<MeterCounterTypeResponse> getById(
            @PathVariable Long meterCounterTypeId) {
        return ResponseEntity.ok(service.getById(meterCounterTypeId));
    }

    @PutMapping("/{meterCounterTypeId}")
    @Operation(summary = "Update a meter counter type")
    public ResponseEntity<MeterCounterTypeResponse> update(
            @PathVariable Long meterCounterTypeId,
            @Valid @RequestBody MeterCounterTypeRequest request) {
        return ResponseEntity.ok(service.update(meterCounterTypeId, request));
    }

    @DeleteMapping("/{meterCounterTypeId}")
    @Operation(summary = "Delete a meter counter type")
    public ResponseEntity<Void> delete(@PathVariable Long meterCounterTypeId) {
        service.delete(meterCounterTypeId);
        return ResponseEntity.noContent().build();
    }
}
