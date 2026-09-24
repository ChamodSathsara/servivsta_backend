package com.gestetner.servvista.Models.entity.attachments;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link EstimateAttachment}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class EstimateAttachmentId implements Serializable {

    private Long estimateId;
    private Long attachmentId;

    public EstimateAttachmentId() {
    }

    public EstimateAttachmentId(Long estimateId, Long attachmentId) {
        this.estimateId = estimateId;
        this.attachmentId = attachmentId;
    }

    public Long getEstimateId() { return estimateId; }
    public void setEstimateId(Long estimateId) { this.estimateId = estimateId; }

    public Long getAttachmentId() { return attachmentId; }
    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstimateAttachmentId)) return false;
        EstimateAttachmentId that = (EstimateAttachmentId) o;
        return Objects.equals(estimateId, that.estimateId) && Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estimateId, attachmentId);
    }
}
