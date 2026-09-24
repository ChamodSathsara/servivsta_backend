package com.gestetner.servvista.Models.entity.customerportal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "portal_refresh_token")
public class PortalRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portal_refresh_token_id", nullable = false, updatable = false)
    private Long portalRefreshTokenId;

    @Column(name = "portal_account_id", nullable = false)
    private Long portalAccountId;

    @Column(name = "token_hash", nullable = false)
    private String tokenHash;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount portalAccount;

    public PortalRefreshToken() {
    }

    public Long getPortalRefreshTokenId() {
        return portalRefreshTokenId;
    }

    public void setPortalRefreshTokenId(Long portalRefreshTokenId) {
        this.portalRefreshTokenId = portalRefreshTokenId;
    }

    public Long getPortalAccountId() {
        return portalAccountId;
    }

    public void setPortalAccountId(Long portalAccountId) {
        this.portalAccountId = portalAccountId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public CustomerPortalAccount getPortalAccount() {
        return portalAccount;
    }

    public void setPortalAccount(CustomerPortalAccount portalAccount) {
        this.portalAccount = portalAccount;
    }

}
