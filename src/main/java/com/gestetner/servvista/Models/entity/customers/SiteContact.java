package com.gestetner.servvista.Models.entity.customers;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "site_contact")
public class SiteContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_contact_id", nullable = false, updatable = false)
    private Long siteContactId;

    @Column(name = "customer_site_id", nullable = false)
    private Long customerSiteId;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "designation")
    private String designation;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_site_id", insertable = false, updatable = false)
    private CustomerSite customerSite;

    public SiteContact() {
    }

    public Long getSiteContactId() {
        return siteContactId;
    }

    public void setSiteContactId(Long siteContactId) {
        this.siteContactId = siteContactId;
    }

    public Long getCustomerSiteId() {
        return customerSiteId;
    }

    public void setCustomerSiteId(Long customerSiteId) {
        this.customerSiteId = customerSiteId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean isPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public CustomerSite getCustomerSite() {
        return customerSite;
    }

    public void setCustomerSite(CustomerSite customerSite) {
        this.customerSite = customerSite;
    }

}
