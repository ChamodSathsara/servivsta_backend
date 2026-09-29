package com.gestetner.servvista.Dto.machines;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MachineRequest(
        @NotNull @Valid CustomerSiteData customerSite,
        @NotNull @Valid SiteContactData siteContact,
        @NotNull @Valid MachineInvoiceData machineInvoice,
        @NotNull @Valid MachineData machine,
        @NotNull @Positive Long performedBy,
        @Size(max = 255) String statusReason
) {
}
