package com.gestetner.servvista.Models.entity.installations;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Installations.InstallationVerificationStatus;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.customers.SiteContact;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.machines.MachineModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "installation_submission")
public class InstallationSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "installation_submission_id", nullable = false, updatable = false)
    private Long installationSubmissionId;
    @Column(name = "installation_job_id", nullable = false) private Long installationJobId;
    @Column(name = "machine_id", nullable = false) private Long machineId;
    @Column(name = "model_id", nullable = false) private Long modelId;
    @Column(name = "customer_site_id", nullable = false) private Long customerSiteId;
    @Column(name = "site_contact_id", nullable = false) private Long siteContactId;
    @Column(name = "install_date", nullable = false) private LocalDate installDate;
    @Column(name = "initial_meter_reading") private Long initialMeterReading;
    @Enumerated(EnumType.STRING) @Column(name = "agreement_type_requested", length = 10)
    private AgreementType agreementTypeRequested;
    @Column(name = "warranty_note", columnDefinition = "text") private String warrantyNote;
    @Column(name = "submitted_by", nullable = false) private Long submittedBy;
    @Column(name = "submitted_at", nullable = false) private LocalDateTime submittedAt;
    @Enumerated(EnumType.STRING) @Column(name = "verification_status", nullable = false, length = 30)
    private InstallationVerificationStatus verificationStatus;
    @Column(name = "verified_by") private Long verifiedBy;
    @Column(name = "verified_at") private LocalDateTime verifiedAt;
    @Column(name = "verification_note", columnDefinition = "text") private String verificationNote;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "installation_job_id", insertable = false, updatable = false) private InstallationJob installationJob;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_id", insertable = false, updatable = false) private Machine machine;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "model_id", insertable = false, updatable = false) private MachineModel model;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_site_id", insertable = false, updatable = false) private CustomerSite customerSite;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "site_contact_id", insertable = false, updatable = false) private SiteContact siteContact;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "submitted_by", insertable = false, updatable = false) private User submittedByRef;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "verified_by", insertable = false, updatable = false) private User verifiedByRef;
}
