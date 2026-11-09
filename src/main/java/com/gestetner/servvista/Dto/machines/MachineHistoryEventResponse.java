package com.gestetner.servvista.Dto.machines;

import java.time.LocalDateTime;

public record MachineHistoryEventResponse(
        String eventType,
        String title,
        String description,
        LocalDateTime occurredAt,
        String performedBy
) {}
