package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.estimates.EstimateRequest;
import com.gestetner.servvista.Dto.estimates.EstimateResponse;
import com.gestetner.servvista.Dto.estimates.EstimateOptionsResponse;
import com.gestetner.servvista.Service.EstimateService;
import com.gestetner.servvista.Service.MachineService;
import com.gestetner.servvista.Service.MachineModelService;
import com.gestetner.servvista.Service.PartCategoryService;
import com.gestetner.servvista.Service.PartService;
import com.gestetner.servvista.Service.StaffUserService;
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
public class EstimateController {

    private final EstimateService service;
    private final MachineService machineService;
    private final StaffUserService staffUserService;
    private final MachineModelService machineModelService;
    private final PartCategoryService partCategoryService;
    private final PartService partService;

    public EstimateController(EstimateService service, MachineService machineService,
            StaffUserService staffUserService, MachineModelService machineModelService,
            PartCategoryService partCategoryService, PartService partService) {
        this.service = service;
        this.machineService = machineService;
        this.staffUserService = staffUserService;
        this.machineModelService = machineModelService;
        this.partCategoryService = partCategoryService;
        this.partService = partService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    @Operation(summary = "Create an estimate with lines")
    public ResponseEntity<EstimateResponse> create(@Valid @RequestBody EstimateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    @Operation(summary = "Get all estimates")
    public List<EstimateResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    @Operation(summary = "Get an estimate by ID")
    public EstimateResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an estimate and replace its lines")
    public EstimateResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EstimateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an estimate and its dependent records")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/options")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    public EstimateOptionsResponse options() {
        return new EstimateOptionsResponse(
                machineService.getAll(),
                staffUserService.getTechnicians(),
                machineModelService.getAll().stream().filter(x -> Boolean.TRUE.equals(x.isActive())).toList(),
                partCategoryService.getAll().stream().filter(x -> Boolean.TRUE.equals(x.isActive())).toList(),
                partService.getAll().stream().filter(x -> Boolean.TRUE.equals(x.isActive())).toList());
    }
}
