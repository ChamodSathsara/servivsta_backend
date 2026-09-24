package com.gestetner.servvista.Models.entity.auditing;

import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.Enums.Auditing.AuditAction;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_log_id", nullable = false, updatable = false)
    private Long auditLogId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 32)
    private AuditAction action;

    @Column(name = "old_values")
    private String oldValues;

    @Column(name = "new_values")
    private String newValues;

    @Column(name = "performed_by_user_id")
    private Long performedByUserId;

    @Column(name = "performed_by_portal_account_id")
    private Long performedByPortalAccountId;

    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_user_id", insertable = false, updatable = false)
    private User performedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount performedByPortalAccount;

    public AuditLog() {
    }

    public Long getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(Long auditLogId) {
        this.auditLogId = auditLogId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getOldValues() {
        return oldValues;
    }

    public void setOldValues(String oldValues) {
        this.oldValues = oldValues;
    }

    public String getNewValues() {
        return newValues;
    }

    public void setNewValues(String newValues) {
        this.newValues = newValues;
    }

    public Long getPerformedByUserId() {
        return performedByUserId;
    }

    public void setPerformedByUserId(Long performedByUserId) {
        this.performedByUserId = performedByUserId;
    }

    public Long getPerformedByPortalAccountId() {
        return performedByPortalAccountId;
    }

    public void setPerformedByPortalAccountId(Long performedByPortalAccountId) {
        this.performedByPortalAccountId = performedByPortalAccountId;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public User getPerformedByUser() {
        return performedByUser;
    }

    public void setPerformedByUser(User performedByUser) {
        this.performedByUser = performedByUser;
    }

    public CustomerPortalAccount getPerformedByPortalAccount() {
        return performedByPortalAccount;
    }

    public void setPerformedByPortalAccount(CustomerPortalAccount performedByPortalAccount) {
        this.performedByPortalAccount = performedByPortalAccount;
    }

}
