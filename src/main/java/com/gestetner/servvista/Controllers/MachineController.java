package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.machines.MachineRequest;
import com.gestetner.servvista.Dto.machines.MachineResponse;
import com.gestetner.servvista.Dto.portal.MachinePortalRegistrationRequest;
import com.gestetner.servvista.Dto.portal.MachinePortalRegistrationResponse;
import com.gestetner.servvista.Service.MachineService;
import com.gestetner.servvista.Service.MachinePortalAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@Tag(name = "Machines")
@PreAuthorize("hasRole('ADMIN')")
public class MachineController {

    private final MachineService machineService;
    private final MachinePortalAuthService portalAuthService;

    public MachineController(
            MachineService machineService,
            MachinePortalAuthService portalAuthService) {
        this.machineService = machineService;
        this.portalAuthService = portalAuthService;
    }

    @PostMapping
    @Operation(summary = "Create a machine with its site, contact, and invoice")
    public ResponseEntity<MachineResponse> create(@Valid @RequestBody MachineRequest request) {
        MachineResponse response = machineService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all machines")
    public ResponseEntity<List<MachineResponse>> getAll() {
        return ResponseEntity.ok(machineService.getAll());
    }

    @GetMapping("/{machineId}")
    @Operation(summary = "Get a machine by ID")
    public ResponseEntity<MachineResponse> getById(@PathVariable Long machineId) {
        return ResponseEntity.ok(machineService.getById(machineId));
    }

    @GetMapping("/search")
    @Operation(summary = "Search machines")
    public ResponseEntity<List<MachineResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(machineService.search(query));
    }

    @PutMapping("/{machineId}")
    @Operation(summary = "Update a machine with its site, contact, and invoice")
    public ResponseEntity<MachineResponse> update(
            @PathVariable Long machineId,
            @Valid @RequestBody MachineRequest request) {
        return ResponseEntity.ok(machineService.update(machineId, request));
    }

    @DeleteMapping("/{machineId}")
    @Operation(summary = "Delete a machine with its site, contacts, and invoice")
    public ResponseEntity<Void> delete(@PathVariable Long machineId) {
        machineService.delete(machineId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{machineId}/portal-registration")
    @Operation(summary = "Register a machine for customer-portal access and issue an OTP")
    public ResponseEntity<MachinePortalRegistrationResponse> registerPortalAccess(
            @PathVariable Long machineId,
            @Valid @RequestBody MachinePortalRegistrationRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                portalAuthService.register(machineId, request, httpRequest.getRemoteAddr()));
    }
}
