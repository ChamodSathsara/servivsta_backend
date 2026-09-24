package com.gestetner.servvista.Models.entity.parts;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "part")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id", nullable = false, updatable = false)
    private Long partId;

    @Column(name = "part_code", nullable = false)
    private String partCode;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;

    public Part() {
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(User createdByRef) {
        this.createdByRef = createdByRef;
    }

}
