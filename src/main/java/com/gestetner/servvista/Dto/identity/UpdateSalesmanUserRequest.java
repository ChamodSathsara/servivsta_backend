package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateSalesmanUserRequest(
        @NotBlank @Size(max = 150) String userName,
        @Size(max = 20) String mobileNumber,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(min = 8, max = 72) String password,
        @NotNull Boolean isActive,
        Division division,
        @NotNull Area area,
        @Positive Long updatedBy,
        @NotBlank @Size(max = 20) String salesmanCode,
        Company company
) implements UpdateUserAccountRequest {
}
