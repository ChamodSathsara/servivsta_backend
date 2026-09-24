package com.gestetner.servvista.Models.entity.breakdowns;

import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.services.SolutionType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownReportedByType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "breakdown")
public class Breakdown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breakdown_id", nullable = false, updatable = false)
    private Long breakdownId;

    @Column(name = "breakdown_number", nullable = false)
    private String breakdownNumber;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "customer_site_id", nullable = false)
    private Long customerSiteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reported_by_type", nullable = false, length = 32)
    private BreakdownReportedByType reportedByType;

    @Column(name = "reported_by_portal_account_id")
    private Long reportedByPortalAccountId;

    @Column(name = "reported_by_user_id")
    private Long reportedByUserId;

    @Column(name = "reported_note")
    private String reportedNote;

    @Column(name = "informed_solution_type_id")
    private Long informedSolutionTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private BreakdownStatus status;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "start_note")
    private String startNote;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "actual_solution_type_id")
    private Long actualSolutionTypeId;

    @Column(name = "solution_note")
    private String solutionNote;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_by")
    private Long cancelledBy;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_site_id", insertable = false, updatable = false)
    private CustomerSite customerSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount reportedByPortalAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_user_id", insertable = false, updatable = false)
    private User reportedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "informed_solution_type_id", insertable = false, updatable = false)
    private SolutionType informedSolutionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by", insertable = false, updatable = false)
    private User approvedByRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_solution_type_id", insertable = false, updatable = false)
    private SolutionType actualSolutionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by", insertable = false, updatable = false)
    private User cancelledByRef;

    public Breakdown() {
    }

    public Long getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(Long breakdownId) {
        this.breakdownId = breakdownId;
    }

    public String getBreakdownNumber() {
        return breakdownNumber;
    }

    public void setBreakdownNumber(String breakdownNumber) {
        this.breakdownNumber = breakdownNumber;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getCustomerSiteId() {
        return customerSiteId;
    }

    public void setCustomerSiteId(Long customerSiteId) {
        this.customerSiteId = customerSiteId;
    }

    public BreakdownReportedByType getReportedByType() {
        return reportedByType;
    }

    public void setReportedByType(BreakdownReportedByType reportedByType) {
        this.reportedByType = reportedByType;
    }

    public Long getReportedByPortalAccountId() {
        return reportedByPortalAccountId;
    }

    public void setReportedByPortalAccountId(Long reportedByPortalAccountId) {
        this.reportedByPortalAccountId = reportedByPortalAccountId;
    }

    public Long getReportedByUserId() {
        return reportedByUserId;
    }

    public void setReportedByUserId(Long reportedByUserId) {
        this.reportedByUserId = reportedByUserId;
    }

    public String getReportedNote() {
        return reportedNote;
    }

    public void setReportedNote(String reportedNote) {
        this.reportedNote = reportedNote;
    }

    public Long getInformedSolutionTypeId() {
        return informedSolutionTypeId;
    }

    public void setInformedSolutionTypeId(Long informedSolutionTypeId) {
        this.informedSolutionTypeId = informedSolutionTypeId;
    }

    public BreakdownStatus getStatus() {
        return status;
    }

    public void setStatus(BreakdownStatus status) {
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

    public Long getActualSolutionTypeId() {
        return actualSolutionTypeId;
    }

    public void setActualSolutionTypeId(Long actualSolutionTypeId) {
        this.actualSolutionTypeId = actualSolutionTypeId;
    }

    public String getSolutionNote() {
        return solutionNote;
    }

    public void setSolutionNote(String solutionNote) {
        this.solutionNote = solutionNote;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(Long cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public CustomerSite getCustomerSite() {
        return customerSite;
    }

    public void setCustomerSite(CustomerSite customerSite) {
        this.customerSite = customerSite;
    }

    public CustomerPortalAccount getReportedByPortalAccount() {
        return reportedByPortalAccount;
    }

    public void setReportedByPortalAccount(CustomerPortalAccount reportedByPortalAccount) {
        this.reportedByPortalAccount = reportedByPortalAccount;
    }

    public User getReportedByUser() {
        return reportedByUser;
    }

    public void setReportedByUser(User reportedByUser) {
        this.reportedByUser = reportedByUser;
    }

    public SolutionType getInformedSolutionType() {
        return informedSolutionType;
    }

    public void setInformedSolutionType(SolutionType informedSolutionType) {
        this.informedSolutionType = informedSolutionType;
    }

    public User getApprovedByRef() {
        return approvedByRef;
    }

    public void setApprovedByRef(User approvedByRef) {
        this.approvedByRef = approvedByRef;
    }

    public SolutionType getActualSolutionType() {
        return actualSolutionType;
    }

    public void setActualSolutionType(SolutionType actualSolutionType) {
        this.actualSolutionType = actualSolutionType;
    }

    public User getCancelledByRef() {
        return cancelledByRef;
    }

    public void setCancelledByRef(User cancelledByRef) {
        this.cancelledByRef = cancelledByRef;
    }

}
