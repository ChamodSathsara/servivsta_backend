package com.gestetner.servvista.Dto.machines;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CustomerSiteData(
        @NotNull @Positive Long customerId,
        @NotBlank @Size(max = 150) String siteName,
        @NotBlank @Size(max = 150) String addressLine1,
        @Size(max = 150) String addressLine2,
        @Size(max = 150) String addressLine3,
        @NotNull Area area,
        @NotNull @Positive Long cityId,
        @DecimalMin("-90.0000000") @DecimalMax("90.0000000") BigDecimal latitude,
        @DecimalMin("-180.0000000") @DecimalMax("180.0000000") BigDecimal longitude,
        Boolean isHeadOffice,
        Boolean isActive
) {
}
