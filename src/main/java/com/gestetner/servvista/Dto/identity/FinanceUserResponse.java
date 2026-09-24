package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.util.List;

public record FinanceUserResponse(
        UserResponse user,
        Long financeId,
        List<Company> companies
) {
}
