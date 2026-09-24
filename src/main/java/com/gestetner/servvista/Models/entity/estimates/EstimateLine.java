package com.gestetner.servvista.Models.entity.estimates;

import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.parts.Part;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateLineStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "estimate_line")
public class EstimateLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_line_id", nullable = false, updatable = false)
    private Long estimateLineId;

    @Column(name = "estimate_id", nullable = false)
    private Long estimateId;

    @Column(name = "part_id")
    private Long partId;

    @Column(name = "pending_part_name")
    private String pendingPartName;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "line_status", nullable = false, length = 32)
    private EstimateLineStatus lineStatus;

    @Column(name = "resolved_by")
    private Long resolvedBy;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", insertable = false, updatable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", insertable = false, updatable = false)
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by", insertable = false, updatable = false)
    private User resolvedByRef;

    public EstimateLine() {
    }

    public Long getEstimateLineId() {
        return estimateLineId;
    }

    public void setEstimateLineId(Long estimateLineId) {
        this.estimateLineId = estimateLineId;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public String getPendingPartName() {
        return pendingPartName;
    }

    public void setPendingPartName(String pendingPartName) {
        this.pendingPartName = pendingPartName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EstimateLineStatus getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(EstimateLineStatus lineStatus) {
        this.lineStatus = lineStatus;
    }

    public Long getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(Long resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public User getResolvedByRef() {
        return resolvedByRef;
    }

    public void setResolvedByRef(User resolvedByRef) {
        this.resolvedByRef = resolvedByRef;
    }

}
