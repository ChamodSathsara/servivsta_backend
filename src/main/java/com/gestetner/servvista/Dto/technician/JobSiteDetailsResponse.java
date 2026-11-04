package com.gestetner.servvista.Dto.technician;

import java.math.BigDecimal;

public record JobSiteDetailsResponse(
        Long customerSiteId,
        String siteName,
        String address,
        String area,
        String city,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean headOffice
) {
}
