package com.gestetner.servvista.Models.entity.customerportal;

import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.machines.Machine;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "portal_account_machine")
public class PortalAccountMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portal_account_machine_id", nullable = false, updatable = false)
    private Long portalAccountMachineId;

    @Column(name = "portal_account_id", nullable = false)
    private Long portalAccountId;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;

    @Column(name = "granted_by")
    private Long grantedBy;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @Column(name = "revoked_by")
    private Long revokedBy;

    @Column(name = "revoke_reason")
    private String revokeReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount portalAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "granted_by", insertable = false, updatable = false)
    private User grantedByRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revoked_by", insertable = false, updatable = false)
    private User revokedByRef;

    public PortalAccountMachine() {
    }

    public Long getPortalAccountMachineId() {
        return portalAccountMachineId;
    }

    public void setPortalAccountMachineId(Long portalAccountMachineId) {
        this.portalAccountMachineId = portalAccountMachineId;
    }

    public Long getPortalAccountId() {
        return portalAccountId;
    }

    public void setPortalAccountId(Long portalAccountId) {
        this.portalAccountId = portalAccountId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public LocalDateTime getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(LocalDateTime grantedAt) {
        this.grantedAt = grantedAt;
    }

    public Long getGrantedBy() {
        return grantedBy;
    }

    public void setGrantedBy(Long grantedBy) {
        this.grantedBy = grantedBy;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public Long getRevokedBy() {
        return revokedBy;
    }

    public void setRevokedBy(Long revokedBy) {
        this.revokedBy = revokedBy;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public CustomerPortalAccount getPortalAccount() {
        return portalAccount;
    }

    public void setPortalAccount(CustomerPortalAccount portalAccount) {
        this.portalAccount = portalAccount;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public User getGrantedByRef() {
        return grantedByRef;
    }

    public void setGrantedByRef(User grantedByRef) {
        this.grantedByRef = grantedByRef;
    }

    public User getRevokedByRef() {
        return revokedByRef;
    }

    public void setRevokedByRef(User revokedByRef) {
        this.revokedByRef = revokedByRef;
    }

}
