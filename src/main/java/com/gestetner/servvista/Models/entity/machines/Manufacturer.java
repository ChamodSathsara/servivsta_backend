package com.gestetner.servvista.Models.entity.machines;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id", nullable = false, updatable = false)
    private Long manufacturerId;

    @Column(name = "manufacturer_name", nullable = false)
    private String manufacturerName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    public Manufacturer() {
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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
