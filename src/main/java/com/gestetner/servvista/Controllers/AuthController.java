package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.auth.LoginRequest;
import com.gestetner.servvista.Dto.auth.LoginResponse;
import com.gestetner.servvista.Dto.auth.RefreshTokenRequest;
import com.gestetner.servvista.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "Login with email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or inactive account")
    })
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.login(request, httpRequest.getRemoteAddr()));
    }

    @PostMapping("/refresh")
    @SecurityRequirements
    @Operation(summary = "Rotate a refresh token and issue a new token pair")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New token pair issued"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid, expired, revoked, or belongs to an inactive user")
    })
    public ResponseEntity<LoginResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.refresh(request, httpRequest.getRemoteAddr()));
    }
}
