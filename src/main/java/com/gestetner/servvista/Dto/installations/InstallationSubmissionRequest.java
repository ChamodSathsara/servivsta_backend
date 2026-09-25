package com.gestetner.servvista.Dto.installations;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Installations.InstallationVerificationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InstallationSubmissionRequest(
        @NotNull @Positive Long installationJobId,
        @NotNull @Positive Long machineId,
        @NotNull @Positive Long modelId,
        @NotNull @Positive Long customerSiteId,
        @NotNull @Positive Long siteContactId,
        @NotNull LocalDate installDate,
        @PositiveOrZero Long initialMeterReading,
        AgreementType agreementTypeRequested,
        String warrantyNote,
        @NotNull @Positive Long submittedBy,
        InstallationVerificationStatus verificationStatus,
        @Positive Long verifiedBy,
        String verificationNote,
        @Size(max = 255) String statusNote
) {
}
