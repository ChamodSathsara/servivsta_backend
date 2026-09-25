package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.catalog.RepRequest;
import com.gestetner.servvista.Dto.catalog.RepResponse;
import com.gestetner.servvista.Service.RepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/reps") @Tag(name = "Representatives") @PreAuthorize("hasRole('ADMIN')")
public class RepController {

    private final RepService service;

    public RepController(RepService service) { this.service = service; }

    @PostMapping @Operation(summary = "Create a representative")
    public ResponseEntity<RepResponse> create(@Valid @RequestBody RepRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping @Operation(summary = "Get all representatives")
    public List<RepResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{repId}") @Operation(summary = "Update a representative")
    public RepResponse update(@PathVariable Long repId, @Valid @RequestBody RepRequest request) {
        return service.update(repId, request);
    }

    @DeleteMapping("/{repId}") @Operation(summary = "Delete a representative")
    public ResponseEntity<Void> delete(@PathVariable Long repId) {
        service.delete(repId);
        return ResponseEntity.noContent().build();
    }
}
