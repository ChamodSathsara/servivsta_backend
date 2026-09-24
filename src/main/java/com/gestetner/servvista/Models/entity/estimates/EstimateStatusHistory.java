package com.gestetner.servvista.Models.entity.estimates;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "estimate_status_history")
public class EstimateStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_status_history_id", nullable = false, updatable = false)
    private Long estimateStatusHistoryId;

    @Column(name = "estimate_id", nullable = false)
    private Long estimateId;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", insertable = false, updatable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", insertable = false, updatable = false)
    private User changedByRef;

    public EstimateStatusHistory() {
    }

    public Long getEstimateStatusHistoryId() {
        return estimateStatusHistoryId;
    }

    public void setEstimateStatusHistoryId(Long estimateStatusHistoryId) {
        this.estimateStatusHistoryId = estimateStatusHistoryId;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
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

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public User getChangedByRef() {
        return changedByRef;
    }

    public void setChangedByRef(User changedByRef) {
        this.changedByRef = changedByRef;
    }

}
