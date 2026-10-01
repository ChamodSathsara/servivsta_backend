package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;
import com.gestetner.servvista.Service.ServiceScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service-schedules")
@Tag(name = "Service Schedules")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR', 'TECHNICIAN')")
public class ServiceScheduleController {

    private final ServiceScheduleService serviceScheduleService;

    public ServiceScheduleController(ServiceScheduleService serviceScheduleService) {
        this.serviceScheduleService = serviceScheduleService;
    }

    @GetMapping
    @Operation(summary = "Get all service schedules")
    public ResponseEntity<List<ServiceScheduleResponse>> getAll() {
        return ResponseEntity.ok(serviceScheduleService.getAll());
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "Get a service schedule by ID")
    public ResponseEntity<ServiceScheduleResponse> getById(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(serviceScheduleService.getById(scheduleId));
    }

    @GetMapping("/agreement/{agreementId}")
    @Operation(summary = "Get service schedules by agreement")
    public ResponseEntity<List<ServiceScheduleResponse>> getByAgreement(
            @PathVariable Long agreementId) {
        return ResponseEntity.ok(serviceScheduleService.getByAgreement(agreementId));
    }

    @GetMapping("/machine/{machineId}")
    @Operation(summary = "Get service schedules by machine")
    public ResponseEntity<List<ServiceScheduleResponse>> getByMachine(
            @PathVariable Long machineId) {
        return ResponseEntity.ok(serviceScheduleService.getByMachine(machineId));
    }
}
