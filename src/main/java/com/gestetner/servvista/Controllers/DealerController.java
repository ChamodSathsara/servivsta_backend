package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.catalog.DealerRequest;
import com.gestetner.servvista.Dto.catalog.DealerResponse;
import com.gestetner.servvista.Service.DealerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/dealers") @Tag(name = "Dealers") @PreAuthorize("hasRole('ADMIN')")
public class DealerController {

    private final DealerService service;

    public DealerController(DealerService service) {
        this.service = service;
    }

    @PostMapping @Operation(summary = "Create a dealer")
    public ResponseEntity<DealerResponse> create(@Valid @RequestBody DealerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request)); }

    @GetMapping @Operation(summary = "Get all dealers")
    public List<DealerResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{dealerId}") @Operation(summary = "Update a dealer")
    public DealerResponse update(@PathVariable Long dealerId, @Valid @RequestBody DealerRequest request) {
        return service.update(dealerId, request);
    }

    @DeleteMapping("/{dealerId}") @Operation(summary = "Delete a dealer")
    public ResponseEntity<Void> delete(@PathVariable Long dealerId) {
        service.delete(dealerId);
        return ResponseEntity.noContent().build();
    }
}
