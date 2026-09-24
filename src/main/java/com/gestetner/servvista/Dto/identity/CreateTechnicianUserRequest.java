package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Identity.TechnicianRole;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateTechnicianUserRequest(
        @NotBlank @Size(max = 150) String userName,
        @Size(max = 20) String mobileNumber,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        Boolean isActive,
        @NotNull Division division,
        @NotNull Area area,
        @Positive Long createdBy,
        @NotNull @Size(min = 1, max = 2) List<@NotNull Company> companies,
        @NotBlank @Size(max = 20) String techCode,
        @NotNull TechnicianRole technicianRole
) implements CompanyAssignmentRequest {
}
