package com.gestetner.servvista.Dto.sales;

import com.gestetner.servvista.Dto.machines.CustomerSiteData;
import com.gestetner.servvista.Dto.machines.SiteContactData;
import com.gestetner.servvista.Models.Enums.Agreements.*;
import com.gestetner.servvista.Models.Enums.Machines.*;
import com.gestetner.servvista.Models.Enums.Organization.*;
import com.gestetner.servvista.Models.Enums.Sales.NewSaleWorkflowStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;

public final class NewSaleWorkflowDtos {
    private NewSaleWorkflowDtos() {}

    public record FinanceStageRequest(
            @NotBlank String serialNumber, @NotNull Company company, @NotNull Long modelId,
            @NotNull Long machineInvoiceId, Long dealerId, Long repId,
            String creditNoteNumber, String machineNote, String statusReason,
            @NotNull Long customerId, @NotNull WarrantyType warrantyType,
            @NotNull LocalDate warrantyStart, @NotNull LocalDate warrantyEnd,
            Integer durationMonths, String warrantyNote, @NotNull Division division,
            @NotNull @Positive Long performedBy) {}

    public record StoresStageRequest(
            @NotBlank @Size(max=20) String machineReferenceNumber,
            @AssertTrue Boolean despatchedFromStores,
            @NotNull @Positive Long performedBy) {}

    public record CoordinatorStageRequest(
            @NotNull @Positive Long mainTechnicianId,
            @NotNull @Positive Long serviceTechnicianId,
            @NotNull LocalDate techniciansAssignedFrom,
            LocalDate expectedInstallDate,
            @NotNull AgreementType agreementType,
            @NotNull LocalDate agreementStart,
            @NotNull LocalDate agreementEnd,
            @NotNull @Positive Integer periodYears,
            @NotNull @Positive Integer visitsPerYear,
            @PositiveOrZero BigDecimal annualPayment,
            @PositiveOrZero BigDecimal fullPayment,
            @PositiveOrZero BigDecimal discount,
            @DecimalMin("0") @DecimalMax("100") BigDecimal vatPercentage,
            @PositiveOrZero BigDecimal vatAmount,
            String agreementNote,
            @NotNull @Positive Long performedBy) {}

    public record TechnicianStageRequest(
            @NotNull @Valid CustomerSiteData customerSite,
            @NotNull @Valid SiteContactData siteContact,
            @NotNull MachineAssignmentType assignmentType,
            @NotNull LocalDate assignedFrom,
            @NotNull @Positive Long performedBy) {}

    public record WorkflowResponse(
            Long workflowId, NewSaleWorkflowStatus status,
            FinanceStageRequest finance, String machineReferenceNumber,
            Boolean despatchedFromStores, CoordinatorStageRequest coordinator,
            Long installationJobId, Long machineId, LocalDateTime createdAt, LocalDateTime updatedAt) {}

    public record WorkflowOptionsResponse(
            java.util.List<com.gestetner.servvista.Dto.catalog.MachineModelResponse> models,
            java.util.List<com.gestetner.servvista.Dto.machines.MachineInvoiceResponse> invoices,
            java.util.List<com.gestetner.servvista.Dto.customers.CustomerResponse> customers,
            java.util.List<com.gestetner.servvista.Dto.catalog.DealerResponse> dealers,
            java.util.List<com.gestetner.servvista.Dto.catalog.RepResponse> representatives,
            java.util.List<com.gestetner.servvista.Dto.identity.TechnicianUserResponse> technicians) {}
}
