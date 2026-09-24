package com.gestetner.servvista.Models.entity.agreements;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "agreement_status_history")
public class AgreementStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_status_history_id", nullable = false, updatable = false)
    private Long agreementStatusHistoryId;

    @Column(name = "agreement_id", nullable = false)
    private Long agreementId;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id", insertable = false, updatable = false)
    private MachineAgreement agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", insertable = false, updatable = false)
    private User changedByRef;

    public AgreementStatusHistory() {
    }

    public Long getAgreementStatusHistoryId() {
        return agreementStatusHistoryId;
    }

    public void setAgreementStatusHistoryId(Long agreementStatusHistoryId) {
        this.agreementStatusHistoryId = agreementStatusHistoryId;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public MachineAgreement getAgreement() {
        return agreement;
    }

    public void setAgreement(MachineAgreement agreement) {
        this.agreement = agreement;
    }

    public User getChangedByRef() {
        return changedByRef;
    }

    public void setChangedByRef(User changedByRef) {
        this.changedByRef = changedByRef;
    }

}
