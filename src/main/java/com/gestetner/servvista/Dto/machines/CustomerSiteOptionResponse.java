package com.gestetner.servvista.Dto.machines;

import com.gestetner.servvista.Models.Enums.Organization.Area;

public record CustomerSiteOptionResponse(
        Long customerSiteId,
        Long customerId,
        String siteName,
        String address,
        Area area
) {}
