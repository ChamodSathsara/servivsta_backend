package com.gestetner.servvista.Dto.organization;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CityRequest(
        @NotNull(message = "Area is required")
        Area area,

        @NotBlank(message = "City name is required")
        @Size(max = 100, message = "City name must not exceed 100 characters")
        String cityName,

        Boolean isActive
) {
}
