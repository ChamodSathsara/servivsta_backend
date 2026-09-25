package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.organization.CityRequest;
import com.gestetner.servvista.Dto.organization.CityResponse;
import com.gestetner.servvista.Service.CityService;
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
@RequestMapping("/api/cities")
@Tag(name = "Cities")
@PreAuthorize("hasRole('ADMIN')")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @Operation(summary = "Create a city")
    public ResponseEntity<CityResponse> create(@Valid @RequestBody CityRequest request) {
        CityResponse response = cityService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all cities")
    public ResponseEntity<List<CityResponse>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @GetMapping("/{cityId}")
    @Operation(summary = "Get a city by ID")
    public ResponseEntity<CityResponse> getById(@PathVariable Long cityId) {
        return ResponseEntity.ok(cityService.getById(cityId));
    }

    @PutMapping("/{cityId}")
    @Operation(summary = "Update a city")
    public ResponseEntity<CityResponse> update(
            @PathVariable Long cityId,
            @Valid @RequestBody CityRequest request) {
        return ResponseEntity.ok(cityService.update(cityId, request));
    }

    @DeleteMapping("/{cityId}")
    @Operation(summary = "Delete a city")
    public ResponseEntity<Void> delete(@PathVariable Long cityId) {
        cityService.delete(cityId);
        return ResponseEntity.noContent().build();
    }
}
