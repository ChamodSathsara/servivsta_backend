package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "agreement_attachment")
@IdClass(AgreementAttachmentId.class)
public class AgreementAttachment {

    @Id
    @Column(name = "agreement_id")
    private Long agreementId;

    @Id
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id", insertable = false, updatable = false)
    private MachineAgreement agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", insertable = false, updatable = false)
    private Attachment attachment;

    public AgreementAttachment() {
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public MachineAgreement getAgreement() {
        return agreement;
    }

    public void setAgreement(MachineAgreement agreement) {
        this.agreement = agreement;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
