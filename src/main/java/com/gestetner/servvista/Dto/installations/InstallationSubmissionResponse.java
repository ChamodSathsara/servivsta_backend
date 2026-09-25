package com.gestetner.servvista.Dto.installations;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Installations.InstallationVerificationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InstallationSubmissionResponse(
        Long installationSubmissionId,
        Long installationJobId,
        String jobNumber,
        InstallationJobStatus jobStatus,
        Long machineId,
        String machineReferenceNumber,
        Long modelId,
        String modelNumber,
        Long customerSiteId,
        String siteName,
        Long siteContactId,
        String contactName,
        LocalDate installDate,
        Long initialMeterReading,
        AgreementType agreementTypeRequested,
        String warrantyNote,
        Long submittedBy,
        LocalDateTime submittedAt,
        InstallationVerificationStatus verificationStatus,
        Long verifiedBy,
        LocalDateTime verifiedAt,
        String verificationNote
) {
}
