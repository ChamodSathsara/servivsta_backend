package com.gestetner.servvista.Dto.installations;

import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record InstallationJobResponse(
        Long installationJobId,
        String jobNumber,
        Company company,
        Division division,
        Long customerId,
        String customerName,
        Long customerSiteId,
        String siteName,
        Long machineInvoiceId,
        String invoiceNumber,
        Long dealerId,
        Long repId,
        Long assignedTechnicianId,
        String technicianName,
        LocalDate expectedInstallDate,
        InstallationJobStatus status,
        Long createdBy,
        LocalDateTime createdAt,
        List<InstallationStatusHistoryResponse> statusHistory
) {
}
