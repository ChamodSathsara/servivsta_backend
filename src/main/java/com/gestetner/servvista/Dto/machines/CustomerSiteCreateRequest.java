package com.gestetner.servvista.Dto.machines;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CustomerSiteCreateRequest(
        @NotNull @Valid CustomerSiteData site,
        @NotNull @Valid SiteContactData contact,
        @NotNull @Positive Long performedBy
) {}
