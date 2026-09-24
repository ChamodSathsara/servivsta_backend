package com.gestetner.servvista.Models.entity.services;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "service_recall")
public class ServiceRecall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_recall_id", nullable = false, updatable = false)
    private Long serviceRecallId;

    @Column(name = "service_schedule_id", nullable = false)
    private Long serviceScheduleId;

    @Column(name = "recall_number", nullable = false)
    private String recallNumber;

    @Column(name = "previous_scheduled_date", nullable = false)
    private LocalDate previousScheduledDate;

    @Column(name = "new_scheduled_date", nullable = false)
    private LocalDate newScheduledDate;

    @Column(name = "recall_reason")
    private String recallReason;

    @Column(name = "recalled_by")
    private Long recalledBy;

    @Column(name = "recalled_at")
    private LocalDateTime recalledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_schedule_id", insertable = false, updatable = false)
    private ServiceSchedule serviceSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recalled_by", insertable = false, updatable = false)
    private User recalledByRef;

    public ServiceRecall() {
    }

    public Long getServiceRecallId() {
        return serviceRecallId;
    }

    public void setServiceRecallId(Long serviceRecallId) {
        this.serviceRecallId = serviceRecallId;
    }

    public Long getServiceScheduleId() {
        return serviceScheduleId;
    }

    public void setServiceScheduleId(Long serviceScheduleId) {
        this.serviceScheduleId = serviceScheduleId;
    }

    public String getRecallNumber() {
        return recallNumber;
    }

    public void setRecallNumber(String recallNumber) {
        this.recallNumber = recallNumber;
    }

    public LocalDate getPreviousScheduledDate() {
        return previousScheduledDate;
    }

    public void setPreviousScheduledDate(LocalDate previousScheduledDate) {
        this.previousScheduledDate = previousScheduledDate;
    }

    public LocalDate getNewScheduledDate() {
        return newScheduledDate;
    }

    public void setNewScheduledDate(LocalDate newScheduledDate) {
        this.newScheduledDate = newScheduledDate;
    }

    public String getRecallReason() {
        return recallReason;
    }

    public void setRecallReason(String recallReason) {
        this.recallReason = recallReason;
    }

    public Long getRecalledBy() {
        return recalledBy;
    }

    public void setRecalledBy(Long recalledBy) {
        this.recalledBy = recalledBy;
    }

    public LocalDateTime getRecalledAt() {
        return recalledAt;
    }

    public void setRecalledAt(LocalDateTime recalledAt) {
        this.recalledAt = recalledAt;
    }

    public ServiceSchedule getServiceSchedule() {
        return serviceSchedule;
    }

    public void setServiceSchedule(ServiceSchedule serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

    public User getRecalledByRef() {
        return recalledByRef;
    }

    public void setRecalledByRef(User recalledByRef) {
        this.recalledByRef = recalledByRef;
    }

}
