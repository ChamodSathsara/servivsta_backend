package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", nullable = false, updatable = false)
    private Long attachmentId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "uploaded_by_user_id")
    private Long uploadedByUserId;

    @Column(name = "uploaded_by_portal_account_id")
    private Long uploadedByPortalAccountId;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_user_id", insertable = false, updatable = false)
    private User uploadedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount uploadedByPortalAccount;

    public Attachment() {
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public Long getUploadedByUserId() {
        return uploadedByUserId;
    }

    public void setUploadedByUserId(Long uploadedByUserId) {
        this.uploadedByUserId = uploadedByUserId;
    }

    public Long getUploadedByPortalAccountId() {
        return uploadedByPortalAccountId;
    }

    public void setUploadedByPortalAccountId(Long uploadedByPortalAccountId) {
        this.uploadedByPortalAccountId = uploadedByPortalAccountId;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUploadedByUser() {
        return uploadedByUser;
    }

    public void setUploadedByUser(User uploadedByUser) {
        this.uploadedByUser = uploadedByUser;
    }

    public CustomerPortalAccount getUploadedByPortalAccount() {
        return uploadedByPortalAccount;
    }

    public void setUploadedByPortalAccount(CustomerPortalAccount uploadedByPortalAccount) {
        this.uploadedByPortalAccount = uploadedByPortalAccount;
    }

}
