package com.gestetner.servvista.Dto.reports;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Identity.TechnicianRole;
import com.gestetner.servvista.Models.Enums.Organization.Area;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MachineAgreementReportRow(
        Long agreementId,
        String agreementNumber,
        AgreementType agreementType,
        AgreementStatus agreementStatus,
        LocalDate agreementStartDate,
        LocalDate agreementEndDate,
        BigDecimal annualPayment,
        BigDecimal fullPayment,
        Long machineId,
        String machineReferenceNumber,
        String serialNumber,
        String modelNumber,
        String modelName,
        Long customerId,
        String customerName,
        Long customerSiteId,
        String siteName,
        Area area,
        Long mainTechnicianId,
        String mainTechnicianName,
        TechnicianRole team
) {
}
