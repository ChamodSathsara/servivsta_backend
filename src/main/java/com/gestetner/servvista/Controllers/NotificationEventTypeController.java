package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.reference.NotificationEventTypeRequest;
import com.gestetner.servvista.Dto.reference.NotificationEventTypeResponse;
import com.gestetner.servvista.Service.NotificationEventTypeService;
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
@RequestMapping("/api/notification-event-types")
@Tag(name = "Notification Event Types")
@PreAuthorize("hasRole('ADMIN')")
public class NotificationEventTypeController {

    private final NotificationEventTypeService service;

    public NotificationEventTypeController(NotificationEventTypeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a notification event type")
    public ResponseEntity<NotificationEventTypeResponse> create(
            @Valid @RequestBody NotificationEventTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all notification event types")
    public ResponseEntity<List<NotificationEventTypeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{notificationEventTypeId}")
    @Operation(summary = "Get a notification event type by ID")
    public ResponseEntity<NotificationEventTypeResponse> getById(
            @PathVariable Long notificationEventTypeId) {
        return ResponseEntity.ok(service.getById(notificationEventTypeId));
    }

    @PutMapping("/{notificationEventTypeId}")
    @Operation(summary = "Update a notification event type")
    public ResponseEntity<NotificationEventTypeResponse> update(
            @PathVariable Long notificationEventTypeId,
            @Valid @RequestBody NotificationEventTypeRequest request) {
        return ResponseEntity.ok(service.update(notificationEventTypeId, request));
    }

    @DeleteMapping("/{notificationEventTypeId}")
    @Operation(summary = "Delete a notification event type")
    public ResponseEntity<Void> delete(@PathVariable Long notificationEventTypeId) {
        service.delete(notificationEventTypeId);
        return ResponseEntity.noContent().build();
    }
}
