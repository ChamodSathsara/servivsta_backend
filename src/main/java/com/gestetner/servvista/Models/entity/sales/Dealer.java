package com.gestetner.servvista.Models.entity.sales;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "dealer")
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dealer_id", nullable = false, updatable = false)
    private Long dealerId;

    @Column(name = "dealer_name", nullable = false)
    private String dealerName;

    @Column(name = "dealer_address")
    private String dealerAddress;

    @Column(name = "dealer_contact_number")
    private String dealerContactNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    public Dealer() {
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealerContactNumber() {
        return dealerContactNumber;
    }

    public void setDealerContactNumber(String dealerContactNumber) {
        this.dealerContactNumber = dealerContactNumber;
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

}
