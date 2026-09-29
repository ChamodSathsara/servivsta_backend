package com.gestetner.servvista.Dto.reference;

import com.gestetner.servvista.Models.Enums.Notifications.NotificationEventCode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationEventTypeRequest(
        @NotNull NotificationEventCode eventCode,
        @Size(max = 255) String description
) {
}
