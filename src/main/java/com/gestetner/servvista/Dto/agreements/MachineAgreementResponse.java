package com.gestetner.servvista.Dto.agreements;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MachineAgreementResponse(
        Long agreementId,
        String agreementNumber,
        Long machineId,
        String machineReferenceNumber,
        String serialNumber,
        AgreementType agreementType,
        LocalDate agreementStartDate,
        LocalDate agreementEndDate,
        Integer agreementPeriodYears,
        Integer visitsPerYear,
        BigDecimal annualPayment,
        BigDecimal fullPayment,
        BigDecimal discount,
        BigDecimal vatPercentage,
        BigDecimal vatAmount,
        AgreementStatus agreementStatus,
        Boolean isActive,
        Long installationJobId,
        String installationJobNumber,
        Long previousAgreementId,
        String previousAgreementNumber,
        String note,
        Long createdBy,
        LocalDateTime createdAt,
        Long updatedBy,
        LocalDateTime updatedAt,
        List<AgreementStatusHistoryResponse> statusHistory
) {
}
