package com.gestetner.servvista.Models.entity.meters;

import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.installations.InstallationSubmission;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import com.gestetner.servvista.Models.Enums.Meters.MeterReadingSource;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "meter_reading")
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_reading_id", nullable = false, updatable = false)
    private Long meterReadingId;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "meter_counter_type_id", nullable = false)
    private Long meterCounterTypeId;

    @Column(name = "reading_value", nullable = false)
    private Long readingValue;

    @Column(name = "reading_datetime", nullable = false)
    private LocalDateTime readingDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_code", nullable = false, length = 32)
    private MeterReadingSource sourceCode;

    @Column(name = "installation_submission_id")
    private Long installationSubmissionId;

    @Column(name = "service_schedule_id")
    private Long serviceScheduleId;

    @Column(name = "breakdown_id")
    private Long breakdownId;

    @Column(name = "captured_by_user_id")
    private Long capturedByUserId;

    @Column(name = "captured_by_portal_account_id")
    private Long capturedByPortalAccountId;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_counter_type_id", insertable = false, updatable = false)
    private MeterCounterType meterCounterType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installation_submission_id", insertable = false, updatable = false)
    private InstallationSubmission installationSubmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_schedule_id", insertable = false, updatable = false)
    private ServiceSchedule serviceSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "captured_by_user_id", insertable = false, updatable = false)
    private User capturedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "captured_by_portal_account_id", insertable = false, updatable = false)
    private CustomerPortalAccount capturedByPortalAccount;

    public MeterReading() {
    }

    public Long getMeterReadingId() {
        return meterReadingId;
    }

    public void setMeterReadingId(Long meterReadingId) {
        this.meterReadingId = meterReadingId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getMeterCounterTypeId() {
        return meterCounterTypeId;
    }

    public void setMeterCounterTypeId(Long meterCounterTypeId) {
        this.meterCounterTypeId = meterCounterTypeId;
    }

    public Long getReadingValue() {
        return readingValue;
    }

    public void setReadingValue(Long readingValue) {
        this.readingValue = readingValue;
    }

    public LocalDateTime getReadingDatetime() {
        return readingDatetime;
    }

    public void setReadingDatetime(LocalDateTime readingDatetime) {
        this.readingDatetime = readingDatetime;
    }

    public MeterReadingSource getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(MeterReadingSource sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Long getInstallationSubmissionId() {
        return installationSubmissionId;
    }

    public void setInstallationSubmissionId(Long installationSubmissionId) {
        this.installationSubmissionId = installationSubmissionId;
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

    public Long getCapturedByUserId() {
        return capturedByUserId;
    }

    public void setCapturedByUserId(Long capturedByUserId) {
        this.capturedByUserId = capturedByUserId;
    }

    public Long getCapturedByPortalAccountId() {
        return capturedByPortalAccountId;
    }

    public void setCapturedByPortalAccountId(Long capturedByPortalAccountId) {
        this.capturedByPortalAccountId = capturedByPortalAccountId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public MeterCounterType getMeterCounterType() {
        return meterCounterType;
    }

    public void setMeterCounterType(MeterCounterType meterCounterType) {
        this.meterCounterType = meterCounterType;
    }

    public InstallationSubmission getInstallationSubmission() {
        return installationSubmission;
    }

    public void setInstallationSubmission(InstallationSubmission installationSubmission) {
        this.installationSubmission = installationSubmission;
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

    public User getCapturedByUser() {
        return capturedByUser;
    }

    public void setCapturedByUser(User capturedByUser) {
        this.capturedByUser = capturedByUser;
    }

    public CustomerPortalAccount getCapturedByPortalAccount() {
        return capturedByPortalAccount;
    }

    public void setCapturedByPortalAccount(CustomerPortalAccount capturedByPortalAccount) {
        this.capturedByPortalAccount = capturedByPortalAccount;
    }

}
