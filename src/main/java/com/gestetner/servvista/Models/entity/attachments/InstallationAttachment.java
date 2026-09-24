package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.installations.InstallationSubmission;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "installation_attachment")
@IdClass(InstallationAttachmentId.class)
public class InstallationAttachment {

    @Id
    @Column(name = "installation_submission_id")
    private Long installationSubmissionId;

    @Id
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installation_submission_id", insertable = false, updatable = false)
    private InstallationSubmission installationSubmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", insertable = false, updatable = false)
    private Attachment attachment;

    public InstallationAttachment() {
    }

    public Long getInstallationSubmissionId() {
        return installationSubmissionId;
    }

    public void setInstallationSubmissionId(Long installationSubmissionId) {
        this.installationSubmissionId = installationSubmissionId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public InstallationSubmission getInstallationSubmission() {
        return installationSubmission;
    }

    public void setInstallationSubmission(InstallationSubmission installationSubmission) {
        this.installationSubmission = installationSubmission;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
