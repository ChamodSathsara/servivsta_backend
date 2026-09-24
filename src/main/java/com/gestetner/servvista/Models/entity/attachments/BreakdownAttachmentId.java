package com.gestetner.servvista.Models.entity.attachments;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link BreakdownAttachment}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class BreakdownAttachmentId implements Serializable {

    private Long breakdownId;
    private Long attachmentId;

    public BreakdownAttachmentId() {
    }

    public BreakdownAttachmentId(Long breakdownId, Long attachmentId) {
        this.breakdownId = breakdownId;
        this.attachmentId = attachmentId;
    }

    public Long getBreakdownId() { return breakdownId; }
    public void setBreakdownId(Long breakdownId) { this.breakdownId = breakdownId; }

    public Long getAttachmentId() { return attachmentId; }
    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BreakdownAttachmentId)) return false;
        BreakdownAttachmentId that = (BreakdownAttachmentId) o;
        return Objects.equals(breakdownId, that.breakdownId) && Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(breakdownId, attachmentId);
    }
}
