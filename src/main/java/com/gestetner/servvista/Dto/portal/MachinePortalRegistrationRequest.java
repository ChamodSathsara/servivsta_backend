package com.gestetner.servvista.Dto.portal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MachinePortalRegistrationRequest(
        @NotNull @Positive Long siteContactId,
        @NotNull @Positive Long grantedBy
) {
}
