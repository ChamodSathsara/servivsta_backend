package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.sales.NewSaleRequest;
import com.gestetner.servvista.Dto.sales.NewSaleResponse;
import com.gestetner.servvista.Service.NewSaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/new-sales", "/new-sales"})
@Tag(name = "New Sales", description = "Complete new-machine sale registration")
@PreAuthorize("hasRole('ADMIN')")
public class NewSaleController {

    private final NewSaleService newSaleService;

    public NewSaleController(NewSaleService newSaleService) {
        this.newSaleService = newSaleService;
    }

    @PostMapping
    @Operation(summary = "Create a complete new machine sale")
    public ResponseEntity<NewSaleResponse> create(@Valid @RequestBody NewSaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newSaleService.create(request));
    }
}
