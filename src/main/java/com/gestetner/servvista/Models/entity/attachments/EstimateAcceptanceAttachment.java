package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.estimates.EstimateAcceptance;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "estimate_acceptance_attachment")
@IdClass(EstimateAcceptanceAttachmentId.class)
public class EstimateAcceptanceAttachment {

    @Id
    @Column(name = "estimate_acceptance_id")
    private Long estimateAcceptanceId;

    @Id
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_acceptance_id", insertable = false, updatable = false)
    private EstimateAcceptance estimateAcceptance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", insertable = false, updatable = false)
    private Attachment attachment;

    public EstimateAcceptanceAttachment() {
    }

    public Long getEstimateAcceptanceId() {
        return estimateAcceptanceId;
    }

    public void setEstimateAcceptanceId(Long estimateAcceptanceId) {
        this.estimateAcceptanceId = estimateAcceptanceId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public EstimateAcceptance getEstimateAcceptance() {
        return estimateAcceptance;
    }

    public void setEstimateAcceptance(EstimateAcceptance estimateAcceptance) {
        this.estimateAcceptance = estimateAcceptance;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
