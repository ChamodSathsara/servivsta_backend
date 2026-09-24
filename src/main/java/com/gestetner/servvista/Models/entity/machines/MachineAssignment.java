package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.installations.InstallationJob;
import com.gestetner.servvista.Models.Enums.Machines.MachineAssignmentType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "machine_assignment")
public class MachineAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_assignment_id", nullable = false, updatable = false)
    private Long machineAssignmentId;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "customer_site_id", nullable = false)
    private Long customerSiteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false, length = 32)
    private MachineAssignmentType assignmentType;

    @Column(name = "agreement_id")
    private Long agreementId;

    @Column(name = "installation_job_id")
    private Long installationJobId;

    @Column(name = "assigned_from", nullable = false)
    private LocalDate assignedFrom;

    @Column(name = "assigned_to")
    private LocalDate assignedTo;

    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_site_id", insertable = false, updatable = false)
    private CustomerSite customerSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id", insertable = false, updatable = false)
    private MachineAgreement agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installation_job_id", insertable = false, updatable = false)
    private InstallationJob installationJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;

    public MachineAssignment() {
    }

    public Long getMachineAssignmentId() {
        return machineAssignmentId;
    }

    public void setMachineAssignmentId(Long machineAssignmentId) {
        this.machineAssignmentId = machineAssignmentId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerSiteId() {
        return customerSiteId;
    }

    public void setCustomerSiteId(Long customerSiteId) {
        this.customerSiteId = customerSiteId;
    }

    public MachineAssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(MachineAssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public Long getInstallationJobId() {
        return installationJobId;
    }

    public void setInstallationJobId(Long installationJobId) {
        this.installationJobId = installationJobId;
    }

    public LocalDate getAssignedFrom() {
        return assignedFrom;
    }

    public void setAssignedFrom(LocalDate assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public LocalDate getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(LocalDate assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerSite getCustomerSite() {
        return customerSite;
    }

    public void setCustomerSite(CustomerSite customerSite) {
        this.customerSite = customerSite;
    }

    public MachineAgreement getAgreement() {
        return agreement;
    }

    public void setAgreement(MachineAgreement agreement) {
        this.agreement = agreement;
    }

    public InstallationJob getInstallationJob() {
        return installationJob;
    }

    public void setInstallationJob(InstallationJob installationJob) {
        this.installationJob = installationJob;
    }

    public User getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(User createdByRef) {
        this.createdByRef = createdByRef;
    }

}
