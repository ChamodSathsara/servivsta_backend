package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.estimates.EstimateRequest;
import com.gestetner.servvista.Dto.estimates.EstimateResponse;
import com.gestetner.servvista.Service.EstimateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estimates")
@Tag(name = "Estimates")
@PreAuthorize("hasRole('ADMIN')")
public class EstimateController {

    private final EstimateService service;

    public EstimateController(EstimateService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create an estimate with lines")
    public ResponseEntity<EstimateResponse> create(@Valid @RequestBody EstimateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all estimates")
    public List<EstimateResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an estimate by ID")
    public EstimateResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an estimate and replace its lines")
    public EstimateResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EstimateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an estimate and its dependent records")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
