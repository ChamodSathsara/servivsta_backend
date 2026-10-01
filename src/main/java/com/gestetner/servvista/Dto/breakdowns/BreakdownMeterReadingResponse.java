package com.gestetner.servvista.Dto.breakdowns;

import com.gestetner.servvista.Models.Enums.Meters.MeterReadingSource;

import java.time.LocalDateTime;

public record BreakdownMeterReadingResponse(
        Long meterReadingId,
        Long machineId,
        Long meterCounterTypeId,
        Long readingValue,
        LocalDateTime readingDatetime,
        MeterReadingSource sourceCode,
        Long breakdownId,
        Long capturedByUserId,
        String note,
        LocalDateTime createdAt
) {
}
