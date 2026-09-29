package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.parts.PartRequest;
import com.gestetner.servvista.Dto.parts.PartResponse;
import com.gestetner.servvista.Service.PartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
@Tag(name = "Parts")
@PreAuthorize("hasRole('ADMIN')")
public class PartController {

    private final PartService service;

    public PartController(PartService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a part and machine-model mappings")
    public ResponseEntity<PartResponse> create(@Valid @RequestBody PartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<PartResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PartResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PartResponse update(@PathVariable Long id, @Valid @RequestBody PartRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
