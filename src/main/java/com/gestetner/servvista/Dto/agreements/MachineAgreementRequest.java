package com.gestetner.servvista.Dto.agreements;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MachineAgreementRequest(
        @NotNull @Positive Long machineId,
        @NotNull AgreementType agreementType,
        @NotNull LocalDate agreementStartDate,
        @NotNull LocalDate agreementEndDate,
        @NotNull @Positive Integer agreementPeriodYears,
        @NotNull @Positive Integer visitsPerYear,
        @PositiveOrZero BigDecimal annualPayment,
        @PositiveOrZero BigDecimal fullPayment,
        @PositiveOrZero BigDecimal discount,
        @DecimalMin("0.00") @DecimalMax("100.00") BigDecimal vatPercentage,
        @PositiveOrZero BigDecimal vatAmount,
        AgreementStatus agreementStatus,
        Boolean isActive,
        @Positive Long installationJobId,
        @Positive Long previousAgreementId,
        String note,
        @NotNull @Positive Long performedBy,
        @Size(max = 255) String statusReason
) {
}
