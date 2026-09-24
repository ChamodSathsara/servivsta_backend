package com.gestetner.servvista.Models.entity.notifications;

import com.gestetner.servvista.Models.Enums.Notifications.NotificationChannel;
import com.gestetner.servvista.Models.Enums.Notifications.NotificationDeliveryStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "notification_delivery")
public class NotificationDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_delivery_id", nullable = false, updatable = false)
    private Long notificationDeliveryId;

    @Column(name = "notification_recipient_id", nullable = false)
    private Long notificationRecipientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 32)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private NotificationDeliveryStatus status;

    @Column(name = "attempted_at")
    private LocalDateTime attemptedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "error_message")
    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_recipient_id", insertable = false, updatable = false)
    private NotificationRecipient notificationRecipient;

    public NotificationDelivery() {
    }

    public Long getNotificationDeliveryId() {
        return notificationDeliveryId;
    }

    public void setNotificationDeliveryId(Long notificationDeliveryId) {
        this.notificationDeliveryId = notificationDeliveryId;
    }

    public Long getNotificationRecipientId() {
        return notificationRecipientId;
    }

    public void setNotificationRecipientId(Long notificationRecipientId) {
        this.notificationRecipientId = notificationRecipientId;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationDeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationDeliveryStatus status) {
        this.status = status;
    }

    public LocalDateTime getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(LocalDateTime attemptedAt) {
        this.attemptedAt = attemptedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public NotificationRecipient getNotificationRecipient() {
        return notificationRecipient;
    }

    public void setNotificationRecipient(NotificationRecipient notificationRecipient) {
        this.notificationRecipient = notificationRecipient;
    }

}
