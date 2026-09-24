package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.identity.*;
import com.gestetner.servvista.Service.StaffUserService;
import com.gestetner.servvista.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User account management endpoints")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;
    private final StaffUserService staffUserService;

    public UserController(UserService userService, StaffUserService staffUserService) {
        this.userService = userService;
        this.staffUserService = staffUserService;
    }

    @PostMapping
    @Operation(summary = "Create a user account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid user details"),
            @ApiResponse(responseCode = "409", description = "Email or another unique value already exists")
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PostMapping("/technician")
    @Operation(summary = "Create a technician user and technician profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Technician user created"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "Email or technician code already exists")
    })
    public ResponseEntity<TechnicianUserResponse> createTechnician(
            @Valid @RequestBody CreateTechnicianUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(staffUserService.createTechnician(request));
    }

    @PostMapping("/coordinator")
    @Operation(summary = "Create a coordinator user and coordinator profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Coordinator user created"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<CoordinatorUserResponse> createCoordinator(
            @Valid @RequestBody CreateCoordinatorUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(staffUserService.createCoordinator(request));
    }

    @PostMapping("/finance")
    @Operation(summary = "Create a finance user and finance profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Finance user created"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<FinanceUserResponse> createFinance(
            @Valid @RequestBody CreateFinanceUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(staffUserService.createFinance(request));
    }

    @PostMapping("/salesman")
    @Operation(summary = "Create a salesman user and salesman profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Salesman user created"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "409", description = "Email or salesman code already exists")
    })
    public ResponseEntity<SalesmanUserResponse> createSalesman(
            @Valid @RequestBody CreateSalesmanUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(staffUserService.createSalesman(request));
    }

    @GetMapping("/technician")
    @Operation(summary = "Get all technician users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technician users returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required")
    })
    public ResponseEntity<List<TechnicianUserResponse>> getTechnicians() {
        return ResponseEntity.ok(staffUserService.getTechnicians());
    }

    @GetMapping("/coordinator")
    @Operation(summary = "Get all coordinator users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinator users returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required")
    })
    public ResponseEntity<List<CoordinatorUserResponse>> getCoordinators() {
        return ResponseEntity.ok(staffUserService.getCoordinators());
    }

    @GetMapping("/finance")
    @Operation(summary = "Get all finance users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Finance users returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required")
    })
    public ResponseEntity<List<FinanceUserResponse>> getFinanceUsers() {
        return ResponseEntity.ok(staffUserService.getFinanceUsers());
    }

    @GetMapping("/salesman")
    @Operation(summary = "Get all salesman users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Salesman users returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required")
    })
    public ResponseEntity<List<SalesmanUserResponse>> getSalesmen() {
        return ResponseEntity.ok(staffUserService.getSalesmen());
    }

    @PutMapping("/technician/{technicianId}")
    @Operation(summary = "Update a technician user and profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technician updated"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Technician not found"),
            @ApiResponse(responseCode = "409", description = "Email or technician code already exists")
    })
    public ResponseEntity<TechnicianUserResponse> updateTechnician(
            @PathVariable Long technicianId,
            @Valid @RequestBody UpdateTechnicianUserRequest request) {
        return ResponseEntity.ok(staffUserService.updateTechnician(technicianId, request));
    }

    @PutMapping("/coordinator/{coordinatorId}")
    @Operation(summary = "Update a coordinator user and profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinator updated"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Coordinator not found"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<CoordinatorUserResponse> updateCoordinator(
            @PathVariable Long coordinatorId,
            @Valid @RequestBody UpdateCoordinatorUserRequest request) {
        return ResponseEntity.ok(staffUserService.updateCoordinator(coordinatorId, request));
    }

    @PutMapping("/finance/{financeId}")
    @Operation(summary = "Update a finance user and profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Finance user updated"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Finance profile not found"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<FinanceUserResponse> updateFinance(
            @PathVariable Long financeId,
            @Valid @RequestBody UpdateFinanceUserRequest request) {
        return ResponseEntity.ok(staffUserService.updateFinance(financeId, request));
    }

    @PutMapping("/salesman/{salesmanId}")
    @Operation(summary = "Update a salesman user and profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Salesman updated"),
            @ApiResponse(responseCode = "400", description = "Invalid details"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "Salesman not found"),
            @ApiResponse(responseCode = "409", description = "Email or salesman code already exists")
    })
    public ResponseEntity<SalesmanUserResponse> updateSalesman(
            @PathVariable Long salesmanId,
            @Valid @RequestBody UpdateSalesmanUserRequest request) {
        return ResponseEntity.ok(staffUserService.updateSalesman(salesmanId, request));
    }
}
