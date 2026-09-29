package com.gestetner.servvista.Dto.breakdowns;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record BreakdownRecallRequest(
        @NotNull LocalDateTime recallDate,
        @Size(max = 255) String recallReason,
        @NotNull @Positive Long recalledBy
) {
}
