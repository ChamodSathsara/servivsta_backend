package com.gestetner.servvista.Dto.reference;

import com.gestetner.servvista.Models.Enums.Meters.MeterCounterCode;

public record MeterCounterTypeResponse(
        Long meterCounterTypeId,
        MeterCounterCode counterCode,
        String counterName,
        Boolean isActive
) {
}
