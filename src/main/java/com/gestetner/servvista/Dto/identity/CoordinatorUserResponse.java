package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Identity.CoordinatorRole;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.util.List;

public record CoordinatorUserResponse(
        UserResponse user,
        Long coordinatorId,
        CoordinatorRole coordinatorRole,
        List<Company> companies
) {
}
