package com.gestetner.servvista.Models.entity.feedback;

import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "field_service_feedback")
public class FieldServiceFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false, updatable = false)
    private Long feedbackId;

    @Column(name = "service_schedule_id")
    private Long serviceScheduleId;

    @Column(name = "breakdown_id")
    private Long breakdownId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "submitted_by_portal_account_id", nullable = false)
    private Long submittedByPortalAccountId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_schedule_id", insertable = false, updatable = false)
    private ServiceSchedule serviceSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by_portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount submittedByPortalAccount;

    public FieldServiceFeedback() {
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getServiceScheduleId() {
        return serviceScheduleId;
    }

    public void setServiceScheduleId(Long serviceScheduleId) {
        this.serviceScheduleId = serviceScheduleId;
    }

    public Long getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(Long breakdownId) {
        this.breakdownId = breakdownId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getSubmittedByPortalAccountId() {
        return submittedByPortalAccountId;
    }

    public void setSubmittedByPortalAccountId(Long submittedByPortalAccountId) {
        this.submittedByPortalAccountId = submittedByPortalAccountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ServiceSchedule getServiceSchedule() {
        return serviceSchedule;
    }

    public void setServiceSchedule(ServiceSchedule serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }

    public CustomerPortalAccount getSubmittedByPortalAccount() {
        return submittedByPortalAccount;
    }

    public void setSubmittedByPortalAccount(CustomerPortalAccount submittedByPortalAccount) {
        this.submittedByPortalAccount = submittedByPortalAccount;
    }

}
