package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.dashboard.DashboardSummaryResponse;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasAnyRole('ADMIN','COORDINATOR','FINANCE','STORES_USER')")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service) { this.service = service; }

    @GetMapping("/summary")
    public DashboardSummaryResponse summary(@RequestParam(required = false) Company company) {
        return service.summary(company);
    }
}
