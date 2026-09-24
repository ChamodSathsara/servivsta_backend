package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Identity.Role;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "User name is required")
        @Size(max = 150, message = "User name must not exceed 150 characters")
        String userName,

        @Size(max = 20, message = "Mobile number must not exceed 20 characters")
        String mobileNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must contain between 8 and 72 characters")
        String password,

        Boolean isActive,

        Division division,

        @NotNull(message = "Area is required")
        Area area,

        @NotNull(message = "Role is required")
        Role role,

        @Positive(message = "Created-by user ID must be positive")
        Long createdBy
) implements UserAccountRequest {
}
