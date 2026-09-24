package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "machine_live_location")
public class MachineLiveLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id", nullable = false, updatable = false)
    private Long machineId;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by_user_id")
    private Long updatedByUserId;

    @Column(name = "updated_by_portal_account_id")
    private Long updatedByPortalAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id", insertable = false, updatable = false)
    private User updatedByUser;

    public MachineLiveLocation() {
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Long updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public Long getUpdatedByPortalAccountId() {
        return updatedByPortalAccountId;
    }

    public void setUpdatedByPortalAccountId(Long updatedByPortalAccountId) {
        this.updatedByPortalAccountId = updatedByPortalAccountId;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public User getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(User updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

}
