package com.gestetner.servvista.Dto.reference;

import com.gestetner.servvista.Models.Enums.Notifications.NotificationEventCode;

public record NotificationEventTypeResponse(
        Long notificationEventTypeId,
        NotificationEventCode eventCode,
        String description
) {
}
