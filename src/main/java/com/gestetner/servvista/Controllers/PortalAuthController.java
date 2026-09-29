package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.portal.PortalOtpVerificationRequest;
import com.gestetner.servvista.Dto.portal.PortalRefreshRequest;
import com.gestetner.servvista.Dto.portal.PortalTokenResponse;
import com.gestetner.servvista.Service.MachinePortalAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portal-auth")
@Tag(name = "Machine Portal Authentication")
public class PortalAuthController {

    private final MachinePortalAuthService portalAuthService;

    public PortalAuthController(MachinePortalAuthService portalAuthService) {
        this.portalAuthService = portalAuthService;
    }

    @PostMapping("/verify-otp")
    @SecurityRequirements
    @Operation(summary = "Verify a machine portal OTP and issue tokens")
    public ResponseEntity<PortalTokenResponse> verifyOtp(
            @Valid @RequestBody PortalOtpVerificationRequest request) {
        return ResponseEntity.ok(portalAuthService.verifyOtp(request));
    }

    @PostMapping("/refresh")
    @SecurityRequirements
    @Operation(summary = "Rotate a portal refresh token")
    public ResponseEntity<PortalTokenResponse> refresh(
            @Valid @RequestBody PortalRefreshRequest request) {
        return ResponseEntity.ok(portalAuthService.refresh(request));
    }
}
