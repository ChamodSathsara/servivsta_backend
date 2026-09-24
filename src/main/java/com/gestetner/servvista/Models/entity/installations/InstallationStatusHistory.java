package com.gestetner.servvista.Models.entity.installations;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "installation_status_history")
public class InstallationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "installation_status_history_id", nullable = false, updatable = false)
    private Long installationStatusHistoryId;

    @Column(name = "installation_job_id", nullable = false)
    private Long installationJobId;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installation_job_id", insertable = false, updatable = false)
    private InstallationJob installationJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", insertable = false, updatable = false)
    private User changedByRef;

    public InstallationStatusHistory() {
    }

    public Long getInstallationStatusHistoryId() {
        return installationStatusHistoryId;
    }

    public void setInstallationStatusHistoryId(Long installationStatusHistoryId) {
        this.installationStatusHistoryId = installationStatusHistoryId;
    }

    public Long getInstallationJobId() {
        return installationJobId;
    }

    public void setInstallationJobId(Long installationJobId) {
        this.installationJobId = installationJobId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public InstallationJob getInstallationJob() {
        return installationJob;
    }

    public void setInstallationJob(InstallationJob installationJob) {
        this.installationJob = installationJob;
    }

    public User getChangedByRef() {
        return changedByRef;
    }

    public void setChangedByRef(User changedByRef) {
        this.changedByRef = changedByRef;
    }

}
