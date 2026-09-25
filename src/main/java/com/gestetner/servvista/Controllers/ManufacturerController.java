package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.catalog.ManufacturerRequest;
import com.gestetner.servvista.Dto.catalog.ManufacturerResponse;
import com.gestetner.servvista.Service.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/manufacturers") @Tag(name = "Manufacturers") @PreAuthorize("hasRole('ADMIN')")
public class ManufacturerController {

    private final ManufacturerService service;

    public ManufacturerController(ManufacturerService service) {
        this.service = service;
    }

    @PostMapping @Operation(summary = "Create a manufacturer")
    public ResponseEntity<ManufacturerResponse> create(@Valid @RequestBody ManufacturerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping @Operation(summary = "Get all manufacturers")
    public List<ManufacturerResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{manufacturerId}") @Operation(summary = "Update a manufacturer")
    public ManufacturerResponse update(@PathVariable Long manufacturerId, @Valid @RequestBody ManufacturerRequest request) {
        return service.update(manufacturerId, request);
    }

    @DeleteMapping("/{manufacturerId}") @Operation(summary = "Delete a manufacturer")
    public ResponseEntity<Void> delete(@PathVariable Long manufacturerId) {
        service.delete(manufacturerId);
        return ResponseEntity.noContent().build();
    }
}
