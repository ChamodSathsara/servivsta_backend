package com.gestetner.servvista.Models.entity.customerportal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "login_otp")
public class LoginOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_otp_id", nullable = false, updatable = false)
    private Long loginOtpId;

    @Column(name = "portal_account_id", nullable = false)
    private Long portalAccountId;

    @Column(name = "otp_code_hash", nullable = false)
    private String otpCodeHash;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "consumed_at")
    private LocalDateTime consumedAt;

    @Column(name = "attempt_count", nullable = false)
    private Integer attemptCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "requested_ip")
    private String requestedIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount portalAccount;

    public LoginOtp() {
    }

    public Long getLoginOtpId() {
        return loginOtpId;
    }

    public void setLoginOtpId(Long loginOtpId) {
        this.loginOtpId = loginOtpId;
    }

    public Long getPortalAccountId() {
        return portalAccountId;
    }

    public void setPortalAccountId(Long portalAccountId) {
        this.portalAccountId = portalAccountId;
    }

    public String getOtpCodeHash() {
        return otpCodeHash;
    }

    public void setOtpCodeHash(String otpCodeHash) {
        this.otpCodeHash = otpCodeHash;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getConsumedAt() {
        return consumedAt;
    }

    public void setConsumedAt(LocalDateTime consumedAt) {
        this.consumedAt = consumedAt;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRequestedIp() {
        return requestedIp;
    }

    public void setRequestedIp(String requestedIp) {
        this.requestedIp = requestedIp;
    }

    public CustomerPortalAccount getPortalAccount() {
        return portalAccount;
    }

    public void setPortalAccount(CustomerPortalAccount portalAccount) {
        this.portalAccount = portalAccount;
    }

}
