package com.gestetner.servvista.Models.entity.breakdowns;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "breakdown_recall")
public class BreakdownRecall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breakdown_recall_id", nullable = false, updatable = false)
    private Long breakdownRecallId;

    @Column(name = "breakdown_id", nullable = false)
    private Long breakdownId;

    @Column(name = "recall_number", nullable = false)
    private String recallNumber;

    @Column(name = "recall_date", nullable = false)
    private LocalDateTime recallDate;

    @Column(name = "recall_reason")
    private String recallReason;

    @Column(name = "recalled_by")
    private Long recalledBy;

    @Column(name = "recalled_at")
    private LocalDateTime recalledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recalled_by", insertable = false, updatable = false)
    private User recalledByRef;

    public BreakdownRecall() {
    }

    public Long getBreakdownRecallId() {
        return breakdownRecallId;
    }

    public void setBreakdownRecallId(Long breakdownRecallId) {
        this.breakdownRecallId = breakdownRecallId;
    }

    public Long getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(Long breakdownId) {
        this.breakdownId = breakdownId;
    }

    public String getRecallNumber() {
        return recallNumber;
    }

    public void setRecallNumber(String recallNumber) {
        this.recallNumber = recallNumber;
    }

    public LocalDateTime getRecallDate() {
        return recallDate;
    }

    public void setRecallDate(LocalDateTime recallDate) {
        this.recallDate = recallDate;
    }

    public String getRecallReason() {
        return recallReason;
    }

    public void setRecallReason(String recallReason) {
        this.recallReason = recallReason;
    }

    public Long getRecalledBy() {
        return recalledBy;
    }

    public void setRecalledBy(Long recalledBy) {
        this.recalledBy = recalledBy;
    }

    public LocalDateTime getRecalledAt() {
        return recalledAt;
    }

    public void setRecalledAt(LocalDateTime recalledAt) {
        this.recalledAt = recalledAt;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }

    public User getRecalledByRef() {
        return recalledByRef;
    }

    public void setRecalledByRef(User recalledByRef) {
        this.recalledByRef = recalledByRef;
    }

}
