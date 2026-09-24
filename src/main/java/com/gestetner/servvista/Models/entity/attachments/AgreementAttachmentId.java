package com.gestetner.servvista.Models.entity.attachments;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link AgreementAttachment}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class AgreementAttachmentId implements Serializable {

    private Long agreementId;
    private Long attachmentId;

    public AgreementAttachmentId() {
    }

    public AgreementAttachmentId(Long agreementId, Long attachmentId) {
        this.agreementId = agreementId;
        this.attachmentId = attachmentId;
    }

    public Long getAgreementId() { return agreementId; }
    public void setAgreementId(Long agreementId) { this.agreementId = agreementId; }

    public Long getAttachmentId() { return attachmentId; }
    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgreementAttachmentId)) return false;
        AgreementAttachmentId that = (AgreementAttachmentId) o;
        return Objects.equals(agreementId, that.agreementId) && Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agreementId, attachmentId);
    }
}
