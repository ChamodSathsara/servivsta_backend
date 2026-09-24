package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.customers.CreateCustomerRequest;
import com.gestetner.servvista.Dto.customers.CustomerResponse;
import com.gestetner.servvista.Dto.customers.UpdateCustomerRequest;
import com.gestetner.servvista.Service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management endpoints")
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Create a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid customer details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "SAGE code already exists")
    })
    public ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all customers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required")
    })
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid customer details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "409", description = "SAGE code already exists")
    })
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(customerService.update(customerId, request));
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "409", description = "Customer is referenced by other records")
    })
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
