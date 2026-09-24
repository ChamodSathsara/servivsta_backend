package com.gestetner.servvista.Models.entity.services;

import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "service_schedule")
public class ServiceSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_schedule_id", nullable = false, updatable = false)
    private Long serviceScheduleId;

    @Column(name = "agreement_id", nullable = false)
    private Long agreementId;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "agreement_year_number", nullable = false)
    private Integer agreementYearNumber;

    @Column(name = "visit_number", nullable = false)
    private Integer visitNumber;

    @Column(name = "expected_visit_date", nullable = false)
    private LocalDate expectedVisitDate;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "actual_visit_date")
    private LocalDate actualVisitDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ServiceScheduleStatus status;

    @Column(name = "assigned_technician_id")
    private Long assignedTechnicianId;

    @Column(name = "start_note")
    private String startNote;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "solution_type_id")
    private Long solutionTypeId;

    @Column(name = "solution_note")
    private String solutionNote;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id", insertable = false, updatable = false)
    private MachineAgreement agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_technician_id", insertable = false, updatable = false)
    private Technician assignedTechnician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_type_id", insertable = false, updatable = false)
    private SolutionType solutionType;

    public ServiceSchedule() {
    }

    public Long getServiceScheduleId() {
        return serviceScheduleId;
    }

    public void setServiceScheduleId(Long serviceScheduleId) {
        this.serviceScheduleId = serviceScheduleId;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Integer getAgreementYearNumber() {
        return agreementYearNumber;
    }

    public void setAgreementYearNumber(Integer agreementYearNumber) {
        this.agreementYearNumber = agreementYearNumber;
    }

    public Integer getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
    }

    public LocalDate getExpectedVisitDate() {
        return expectedVisitDate;
    }

    public void setExpectedVisitDate(LocalDate expectedVisitDate) {
        this.expectedVisitDate = expectedVisitDate;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalDate getActualVisitDate() {
        return actualVisitDate;
    }

    public void setActualVisitDate(LocalDate actualVisitDate) {
        this.actualVisitDate = actualVisitDate;
    }

    public ServiceScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceScheduleStatus status) {
        this.status = status;
    }

    public Long getAssignedTechnicianId() {
        return assignedTechnicianId;
    }

    public void setAssignedTechnicianId(Long assignedTechnicianId) {
        this.assignedTechnicianId = assignedTechnicianId;
    }

    public String getStartNote() {
        return startNote;
    }

    public void setStartNote(String startNote) {
        this.startNote = startNote;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getSolutionTypeId() {
        return solutionTypeId;
    }

    public void setSolutionTypeId(Long solutionTypeId) {
        this.solutionTypeId = solutionTypeId;
    }

    public String getSolutionNote() {
        return solutionNote;
    }

    public void setSolutionNote(String solutionNote) {
        this.solutionNote = solutionNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public MachineAgreement getAgreement() {
        return agreement;
    }

    public void setAgreement(MachineAgreement agreement) {
        this.agreement = agreement;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Technician getAssignedTechnician() {
        return assignedTechnician;
    }

    public void setAssignedTechnician(Technician assignedTechnician) {
        this.assignedTechnician = assignedTechnician;
    }

    public SolutionType getSolutionType() {
        return solutionType;
    }

    public void setSolutionType(SolutionType solutionType) {
        this.solutionType = solutionType;
    }

}
