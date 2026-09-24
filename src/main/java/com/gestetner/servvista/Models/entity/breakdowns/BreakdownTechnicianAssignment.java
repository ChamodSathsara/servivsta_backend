package com.gestetner.servvista.Models.entity.breakdowns;

import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownAssignmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "breakdown_technician_assignment")
public class BreakdownTechnicianAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breakdown_technician_assignment_id", nullable = false, updatable = false)
    private Long breakdownTechnicianAssignmentId;

    @Column(name = "breakdown_id", nullable = false)
    private Long breakdownId;

    @Column(name = "technician_id", nullable = false)
    private Long technicianId;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "assigned_by")
    private Long assignedBy;

    @Column(name = "unassigned_at")
    private LocalDateTime unassignedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false, length = 32)
    private BreakdownAssignmentStatus assignmentStatus;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", insertable = false, updatable = false)
    private Technician technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", insertable = false, updatable = false)
    private User assignedByRef;

    public BreakdownTechnicianAssignment() {
    }

    public Long getBreakdownTechnicianAssignmentId() {
        return breakdownTechnicianAssignmentId;
    }

    public void setBreakdownTechnicianAssignmentId(Long breakdownTechnicianAssignmentId) {
        this.breakdownTechnicianAssignmentId = breakdownTechnicianAssignmentId;
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

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getUnassignedAt() {
        return unassignedAt;
    }

    public void setUnassignedAt(LocalDateTime unassignedAt) {
        this.unassignedAt = unassignedAt;
    }

    public BreakdownAssignmentStatus getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(BreakdownAssignmentStatus assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public User getAssignedByRef() {
        return assignedByRef;
    }

    public void setAssignedByRef(User assignedByRef) {
        this.assignedByRef = assignedByRef;
    }

}
