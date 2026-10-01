package com.gestetner.servvista.Dto.meters;

import com.gestetner.servvista.Models.Enums.Meters.MeterCounterCode;
import com.gestetner.servvista.Models.Enums.Meters.MeterReadingSource;

import java.time.LocalDateTime;

public record MeterReadingResponse(
        Long meterReadingId,
        Long machineId,
        String machineReferenceNumber,
        Long meterCounterTypeId,
        MeterCounterCode counterCode,
        String counterName,
        Long readingValue,
        LocalDateTime readingDatetime,
        MeterReadingSource sourceCode,
        Long installationSubmissionId,
        Long serviceScheduleId,
        Long breakdownId,
        Long capturedByUserId,
        Long capturedByPortalAccountId,
        String note,
        LocalDateTime createdAt
) {
}
