package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.util.List;

public record StoresUserResponse(
        UserResponse user,
        Long storesUserId,
        List<Company> companies
) {
}
