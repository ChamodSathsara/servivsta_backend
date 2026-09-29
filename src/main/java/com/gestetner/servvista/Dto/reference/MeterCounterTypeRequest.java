package com.gestetner.servvista.Dto.reference;

import com.gestetner.servvista.Models.Enums.Meters.MeterCounterCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MeterCounterTypeRequest(
        @NotNull MeterCounterCode counterCode,
        @NotBlank @Size(max = 60) String counterName,
        Boolean isActive
) {
}
