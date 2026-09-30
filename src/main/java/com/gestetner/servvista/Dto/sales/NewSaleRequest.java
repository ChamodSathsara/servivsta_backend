package com.gestetner.servvista.Dto.sales;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Machines.MachineAssignmentType;
import com.gestetner.servvista.Models.Enums.Machines.MachineStatus;
import com.gestetner.servvista.Models.Enums.Machines.WarrantyStatus;
import com.gestetner.servvista.Models.Enums.Machines.WarrantyType;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Dto.machines.CustomerSiteData;
import com.gestetner.servvista.Dto.machines.SiteContactData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewSaleRequest(
        @NotNull @Valid MachineData machine,
        @NotNull @Valid CustomerSiteData customerSite,
        @NotNull @Valid SiteContactData siteContact,
        @NotNull @Valid AssignmentData assignment,
        @NotNull @Valid TechnicianData technicians,
        @NotNull @Valid WarrantyData warranty,
        @NotNull @Valid LocationData liveLocation,
        @NotNull @Valid InstallationData installation,
        @NotNull @Valid AgreementData agreement,
        @NotNull @Positive Long performedBy,
        @Size(max = 255) String machineStatusReason
) {
    public record MachineData(
            @NotBlank @Size(max = 100) String serialNumber,
            @NotNull Company company,
            @NotNull Division division,
            @NotNull @Positive Long modelId,
            @NotNull MachineStatus currentStatus,
            @NotNull @Positive Long machineInvoiceId,
            @Positive Long dealerId,
            @Positive Long repId,
            @Positive Long salesmanId,
            LocalDate originalInstallDate,
            String note,
            @Size(max = 50) String creditNoteNumber
    ) {
    }

    public record AssignmentData(
            @NotNull @Positive Long customerId,
            @NotNull MachineAssignmentType assignmentType,
            @NotNull LocalDate assignedFrom
    ) {
    }

    public record TechnicianData(
            @NotNull @Positive Long mainTechnicianId,
            @NotNull @Positive Long serviceTechnicianId,
            @NotNull LocalDate assignedFrom
    ) {
    }

    public record WarrantyData(
            @NotNull WarrantyType warrantyType,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @Positive Integer durationMonths,
            WarrantyStatus status,
            String note
    ) {
    }

    public record LocationData(
            @NotNull @DecimalMin("-90.0000000") @DecimalMax("90.0000000") BigDecimal latitude,
            @NotNull @DecimalMin("-180.0000000") @DecimalMax("180.0000000") BigDecimal longitude
    ) {
    }

    public record InstallationData(
            LocalDate expectedInstallDate,
            InstallationJobStatus status,
            @Size(max = 255) String statusNote
    ) {
    }

    public record AgreementData(
            @NotNull AgreementType agreementType,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @NotNull @Positive Integer periodYears,
            @NotNull @Positive Integer visitsPerYear,
            @PositiveOrZero BigDecimal annualPayment,
            @PositiveOrZero BigDecimal fullPayment,
            @PositiveOrZero BigDecimal discount,
            @DecimalMin("0.00") @DecimalMax("100.00") BigDecimal vatPercentage,
            @PositiveOrZero BigDecimal vatAmount,
            AgreementStatus status,
            Boolean isActive,
            @Positive Long previousAgreementId,
            String note,
            @Size(max = 255) String statusReason
    ) {
    }
}
