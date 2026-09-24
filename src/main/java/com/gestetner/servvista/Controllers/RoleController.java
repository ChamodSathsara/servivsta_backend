package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Models.Enums.Identity.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Available user roles")
public class RoleController {

    @GetMapping
    @Operation(summary = "Get all available user roles")
    @ApiResponse(responseCode = "200", description = "Roles returned")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(Arrays.asList(Role.values()));
    }

    @GetMapping("/{role}")
    @Operation(summary = "Get a user role enum value")
    @ApiResponse(responseCode = "200", description = "Role returned")
    public ResponseEntity<Role> getRole(@PathVariable Role role) {
        return ResponseEntity.ok(role);
    }
}
