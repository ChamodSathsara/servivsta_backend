package com.gestetner.servvista.Models.entity.notifications;

import com.gestetner.servvista.Models.Enums.Notifications.NotificationEventCode;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "notification_event_type")
public class NotificationEventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_event_type_id", nullable = false, updatable = false)
    private Long notificationEventTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_code", nullable = false, length = 32)
    private NotificationEventCode eventCode;

    @Column(name = "description")
    private String description;

    public NotificationEventType() {
    }

    public Long getNotificationEventTypeId() {
        return notificationEventTypeId;
    }

    public void setNotificationEventTypeId(Long notificationEventTypeId) {
        this.notificationEventTypeId = notificationEventTypeId;
    }

    public NotificationEventCode getEventCode() {
        return eventCode;
    }

    public void setEventCode(NotificationEventCode eventCode) {
        this.eventCode = eventCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
