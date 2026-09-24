package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "breakdown_attachment")
@IdClass(BreakdownAttachmentId.class)
public class BreakdownAttachment {

    @Id
    @Column(name = "breakdown_id")
    private Long breakdownId;

    @Id
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", insertable = false, updatable = false)
    private Attachment attachment;

    public BreakdownAttachment() {
    }

    public Long getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(Long breakdownId) {
        this.breakdownId = breakdownId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
