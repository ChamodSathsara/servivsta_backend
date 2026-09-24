package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.estimates.Estimate;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "estimate_attachment")
@IdClass(EstimateAttachmentId.class)
public class EstimateAttachment {

    @Id
    @Column(name = "estimate_id")
    private Long estimateId;

    @Id
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", insertable = false, updatable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", insertable = false, updatable = false)
    private Attachment attachment;

    public EstimateAttachment() {
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
