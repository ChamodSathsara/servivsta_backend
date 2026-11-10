package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.parts.PartCategoryRequest;
import com.gestetner.servvista.Dto.parts.PartCategoryResponse;
import com.gestetner.servvista.Service.PartCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part-categories")
@Tag(name = "Part Categories")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
public class PartCategoryController {
    private final PartCategoryService service;
    public PartCategoryController(PartCategoryService service) { this.service = service; }
    @PostMapping public ResponseEntity<PartCategoryResponse> create(@Valid @RequestBody PartCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @GetMapping public List<PartCategoryResponse> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public PartCategoryResponse get(@PathVariable Long id) { return service.getById(id); }
    @PutMapping("/{id}") public PartCategoryResponse update(@PathVariable Long id, @Valid @RequestBody PartCategoryRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
