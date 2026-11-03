package com.gestetner.servvista.Dto.services;

public record ServiceVisitStartResponse(
        ServiceScheduleResponse serviceSchedule,
        ServiceVisitMeterReadingResponse meterReading
) {
}
