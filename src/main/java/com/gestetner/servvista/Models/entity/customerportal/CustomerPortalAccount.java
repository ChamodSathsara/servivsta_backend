package com.gestetner.servvista.Models.entity.customerportal;

import com.gestetner.servvista.Models.entity.customers.SiteContact;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "customer_portal_account")
public class CustomerPortalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portal_account_id", nullable = false, updatable = false)
    private Long portalAccountId;

    @Column(name = "site_contact_id", nullable = false)
    private Long siteContactId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_contact_id", insertable = false, updatable = false)
    private SiteContact siteContact;

    public CustomerPortalAccount() {
    }

    public Long getPortalAccountId() {
        return portalAccountId;
    }

    public void setPortalAccountId(Long portalAccountId) {
        this.portalAccountId = portalAccountId;
    }

    public Long getSiteContactId() {
        return siteContactId;
    }

    public void setSiteContactId(Long siteContactId) {
        this.siteContactId = siteContactId;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SiteContact getSiteContact() {
        return siteContact;
    }

    public void setSiteContact(SiteContact siteContact) {
        this.siteContact = siteContact;
    }

}
