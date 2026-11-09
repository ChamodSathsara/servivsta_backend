package com.gestetner.servvista.Dto.machines;

import com.gestetner.servvista.Models.Enums.Machines.MachineAssignmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record MachineCustomerTransferRequest(
        @NotNull @Positive Long customerId,
        @NotNull @Positive Long customerSiteId,
        @NotNull MachineAssignmentType assignmentType,
        @NotNull LocalDate effectiveDate,
        @NotNull @Positive Long performedBy,
        String reason
) {}
