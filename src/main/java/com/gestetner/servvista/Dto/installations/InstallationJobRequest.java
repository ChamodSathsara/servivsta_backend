package com.gestetner.servvista.Dto.installations;

import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InstallationJobRequest(
        @NotNull Company company,
        @NotNull Division division,
        @NotNull @Positive Long customerId,
        @Positive Long customerSiteId,
        @Positive Long machineInvoiceId,
        @Positive Long dealerId,
        @Positive Long repId,
        @NotNull @Positive Long assignedTechnicianId,
        LocalDate expectedInstallDate,
        InstallationJobStatus status,
        @NotNull @Positive Long performedBy,
        @Size(max = 255) String statusNote
) {
}
