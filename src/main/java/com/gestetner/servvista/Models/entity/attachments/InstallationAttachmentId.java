package com.gestetner.servvista.Models.entity.attachments;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link InstallationAttachment}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class InstallationAttachmentId implements Serializable {

    private Long installationSubmissionId;
    private Long attachmentId;

    public InstallationAttachmentId() {
    }

    public InstallationAttachmentId(Long installationSubmissionId, Long attachmentId) {
        this.installationSubmissionId = installationSubmissionId;
        this.attachmentId = attachmentId;
    }

    public Long getInstallationSubmissionId() { return installationSubmissionId; }
    public void setInstallationSubmissionId(Long installationSubmissionId) { this.installationSubmissionId = installationSubmissionId; }

    public Long getAttachmentId() { return attachmentId; }
    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstallationAttachmentId)) return false;
        InstallationAttachmentId that = (InstallationAttachmentId) o;
        return Objects.equals(installationSubmissionId, that.installationSubmissionId) && Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(installationSubmissionId, attachmentId);
    }
}
