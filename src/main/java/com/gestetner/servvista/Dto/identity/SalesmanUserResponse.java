package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.util.List;

public record SalesmanUserResponse(
        UserResponse user,
        Long salesmanId,
        String salesmanCode,
        Company company,
        List<Company> companies
) {
}
