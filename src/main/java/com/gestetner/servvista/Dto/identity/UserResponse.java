package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Identity.Role;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;

import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String userName,
        String mobileNumber,
        String email,
        Boolean isActive,
        Division division,
        Area area,
        Role role,
        LocalDateTime createdAt,
        Long createdBy
) {
}
