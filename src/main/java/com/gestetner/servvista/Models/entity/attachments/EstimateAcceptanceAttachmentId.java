package com.gestetner.servvista.Models.entity.attachments;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link EstimateAcceptanceAttachment}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class EstimateAcceptanceAttachmentId implements Serializable {

    private Long estimateAcceptanceId;
    private Long attachmentId;

    public EstimateAcceptanceAttachmentId() {
    }

    public EstimateAcceptanceAttachmentId(Long estimateAcceptanceId, Long attachmentId) {
        this.estimateAcceptanceId = estimateAcceptanceId;
        this.attachmentId = attachmentId;
    }

    public Long getEstimateAcceptanceId() { return estimateAcceptanceId; }
    public void setEstimateAcceptanceId(Long estimateAcceptanceId) { this.estimateAcceptanceId = estimateAcceptanceId; }

    public Long getAttachmentId() { return attachmentId; }
    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstimateAcceptanceAttachmentId)) return false;
        EstimateAcceptanceAttachmentId that = (EstimateAcceptanceAttachmentId) o;
        return Objects.equals(estimateAcceptanceId, that.estimateAcceptanceId) && Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estimateAcceptanceId, attachmentId);
    }
}
