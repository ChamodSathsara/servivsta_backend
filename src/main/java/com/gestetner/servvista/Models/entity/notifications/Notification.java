package com.gestetner.servvista.Models.entity.notifications;

import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import com.gestetner.servvista.Models.entity.estimates.Estimate;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.installations.InstallationJob;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, updatable = false)
    private Long notificationId;

    @Column(name = "event_type_id", nullable = false)
    private Long eventTypeId;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "installation_job_id")
    private Long installationJobId;

    @Column(name = "service_schedule_id")
    private Long serviceScheduleId;

    @Column(name = "breakdown_id")
    private Long breakdownId;

    @Column(name = "estimate_id")
    private Long estimateId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id", insertable = false, updatable = false)
    private NotificationEventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installation_job_id", insertable = false, updatable = false)
    private InstallationJob installationJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_schedule_id", insertable = false, updatable = false)
    private ServiceSchedule serviceSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id", insertable = false, updatable = false)
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", insertable = false, updatable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;

    public Notification() {
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getInstallationJobId() {
        return installationJobId;
    }

    public void setInstallationJobId(Long installationJobId) {
        this.installationJobId = installationJobId;
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

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public NotificationEventType getEventType() {
        return eventType;
    }

    public void setEventType(NotificationEventType eventType) {
        this.eventType = eventType;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public InstallationJob getInstallationJob() {
        return installationJob;
    }

    public void setInstallationJob(InstallationJob installationJob) {
        this.installationJob = installationJob;
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

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public User getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(User createdByRef) {
        this.createdByRef = createdByRef;
    }

}
