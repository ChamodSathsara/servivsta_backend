package com.gestetner.servvista.Models.entity.sales;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "rep")
public class Rep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rep_id", nullable = false, updatable = false)
    private Long repId;

    @Column(name = "rep_code", nullable = false)
    private String repCode;

    @Column(name = "rep_name", nullable = false)
    private String repName;

    @Column(name = "rep_mobile_number")
    private String repMobileNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    public Rep() {
    }

    public Long getRepId() {
        return repId;
    }

    public void setRepId(Long repId) {
        this.repId = repId;
    }

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepMobileNumber() {
        return repMobileNumber;
    }

    public void setRepMobileNumber(String repMobileNumber) {
        this.repMobileNumber = repMobileNumber;
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
