package com.gestetner.servvista.Models.entity.estimates;

import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateAcceptedByType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "estimate_acceptance")
public class EstimateAcceptance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_acceptance_id", nullable = false, updatable = false)
    private Long estimateAcceptanceId;

    @Column(name = "estimate_id", nullable = false)
    private Long estimateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "accepted_by_type", nullable = false, length = 32)
    private EstimateAcceptedByType acceptedByType;

    @Column(name = "portal_account_id")
    private Long portalAccountId;

    @Column(name = "recorded_by_user_id")
    private Long recordedByUserId;

    @Column(name = "accepted_at", nullable = false)
    private LocalDateTime acceptedAt;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", insertable = false, updatable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount portalAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by_user_id", insertable = false, updatable = false)
    private User recordedByUser;

    public EstimateAcceptance() {
    }

    public Long getEstimateAcceptanceId() {
        return estimateAcceptanceId;
    }

    public void setEstimateAcceptanceId(Long estimateAcceptanceId) {
        this.estimateAcceptanceId = estimateAcceptanceId;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public EstimateAcceptedByType getAcceptedByType() {
        return acceptedByType;
    }

    public void setAcceptedByType(EstimateAcceptedByType acceptedByType) {
        this.acceptedByType = acceptedByType;
    }

    public Long getPortalAccountId() {
        return portalAccountId;
    }

    public void setPortalAccountId(Long portalAccountId) {
        this.portalAccountId = portalAccountId;
    }

    public Long getRecordedByUserId() {
        return recordedByUserId;
    }

    public void setRecordedByUserId(Long recordedByUserId) {
        this.recordedByUserId = recordedByUserId;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public CustomerPortalAccount getPortalAccount() {
        return portalAccount;
    }

    public void setPortalAccount(CustomerPortalAccount portalAccount) {
        this.portalAccount = portalAccount;
    }

    public User getRecordedByUser() {
        return recordedByUser;
    }

    public void setRecordedByUser(User recordedByUser) {
        this.recordedByUser = recordedByUser;
    }

}
