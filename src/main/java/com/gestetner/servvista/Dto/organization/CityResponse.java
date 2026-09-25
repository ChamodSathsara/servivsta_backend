package com.gestetner.servvista.Dto.organization;

import com.gestetner.servvista.Models.Enums.Organization.Area;

public record CityResponse(
        Long cityId,
        Area area,
        String cityName,
        Boolean isActive
) {
}
