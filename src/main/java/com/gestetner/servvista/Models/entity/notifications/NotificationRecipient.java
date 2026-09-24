package com.gestetner.servvista.Models.entity.notifications;

import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.Enums.Notifications.NotificationChannel;
import com.gestetner.servvista.Models.Enums.Notifications.NotificationRecipientType;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "notification_recipient")
public class NotificationRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_recipient_id", nullable = false, updatable = false)
    private Long notificationRecipientId;

    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false, length = 32)
    private NotificationRecipientType recipientType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "portal_account_id")
    private Long portalAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 32)
    private NotificationChannel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", insertable = false, updatable = false)
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount portalAccount;

    public NotificationRecipient() {
    }

    public Long getNotificationRecipientId() {
        return notificationRecipientId;
    }

    public void setNotificationRecipientId(Long notificationRecipientId) {
        this.notificationRecipientId = notificationRecipientId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationRecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(NotificationRecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPortalAccountId() {
        return portalAccountId;
    }

    public void setPortalAccountId(Long portalAccountId) {
        this.portalAccountId = portalAccountId;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomerPortalAccount getPortalAccount() {
        return portalAccount;
    }

    public void setPortalAccount(CustomerPortalAccount portalAccount) {
        this.portalAccount = portalAccount;
    }

}
