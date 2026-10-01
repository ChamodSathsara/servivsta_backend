package com.gestetner.servvista.Dto.breakdowns;

public record BreakdownStartResponse(
        BreakdownResponse breakdown,
        BreakdownMeterReadingResponse meterReading
) {
}
