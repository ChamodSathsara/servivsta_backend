package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Identity.TechnicianRole;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.util.List;

public record TechnicianUserResponse(
        UserResponse user,
        Long technicianId,
        String techCode,
        TechnicianRole technicianRole,
        List<Company> companies
) {
}
