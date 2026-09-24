package com.gestetner.servvista.Models.entity.estimates;

import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import com.gestetner.servvista.Models.Enums.Estimates.EstimateStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "estimate")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_id", nullable = false, updatable = false)
    private Long estimateId;

    @Column(name = "estimate_number", nullable = false)
    private String estimateNumber;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "service_schedule_id")
    private Long serviceScheduleId;

    @Column(name = "breakdown_id")
    private Long breakdownId;

    @Column(name = "technician_id", nullable = false)
    private Long technicianId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private EstimateStatus status;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_schedule_id", insertable = false, updatable = false)
    private ServiceSchedule serviceSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", insertable = false, updatable = false)
    private Technician technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by", insertable = false, updatable = false)
    private User approvedByRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;

    public Estimate() {
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getServiceScheduleId() {
        return serviceScheduleId;
    }

    public void setServiceScheduleId(Long serviceScheduleId) {
        this.serviceScheduleId = serviceScheduleId;
    }

    public Long getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(Long breakdownId) {
        this.breakdownId = breakdownId;
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }

    public EstimateStatus getStatus() {
        return status;
    }

    public void setStatus(EstimateStatus status) {
        this.status = status;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ServiceSchedule getServiceSchedule() {
        return serviceSchedule;
    }

    public void setServiceSchedule(ServiceSchedule serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public User getApprovedByRef() {
        return approvedByRef;
    }

    public void setApprovedByRef(User approvedByRef) {
        this.approvedByRef = approvedByRef;
    }

    public User getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(User createdByRef) {
        this.createdByRef = createdByRef;
    }

}
