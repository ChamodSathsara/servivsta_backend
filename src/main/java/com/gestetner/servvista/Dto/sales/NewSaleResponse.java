package com.gestetner.servvista.Dto.sales;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Machines.MachineStatus;
import com.gestetner.servvista.Models.Enums.Machines.WarrantyStatus;

import java.time.LocalDateTime;

public record NewSaleResponse(
        Long machineId,
        String machineReferenceNumber,
        String serialNumber,
        MachineStatus machineStatus,
        Long customerSiteId,
        Long siteContactId,
        Long machineStatusHistoryId,
        Long machineAssignmentId,
        Long mainTechnicianAssignmentId,
        Long serviceTechnicianAssignmentId,
        Long machineWarrantyId,
        WarrantyStatus warrantyStatus,
        Long liveLocationMachineId,
        Long installationJobId,
        String installationJobNumber,
        InstallationJobStatus installationStatus,
        Long agreementId,
        String agreementNumber,
        AgreementStatus agreementStatus,
        LocalDateTime createdAt
) {
}
