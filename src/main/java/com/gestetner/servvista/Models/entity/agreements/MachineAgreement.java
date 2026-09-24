package com.gestetner.servvista.Models.entity.agreements;

import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.installations.InstallationJob;
import com.gestetner.servvista.Models.entity.machines.Machine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "machine_agreement")
public class MachineAgreement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id", nullable = false, updatable = false) private Long agreementId;
    @Column(name = "agreement_number", nullable = false, unique = true, length = 20) private String agreementNumber;
    @Column(name = "machine_id", nullable = false) private Long machineId;
    @Enumerated(EnumType.STRING) @Column(name = "agreement_type", nullable = false, length = 10) private AgreementType agreementType;
    @Column(name = "agreement_start_date", nullable = false) private LocalDate agreementStartDate;
    @Column(name = "agreement_end_date", nullable = false) private LocalDate agreementEndDate;
    @Column(name = "agreement_period_years", nullable = false) private Integer agreementPeriodYears;
    @Column(name = "visits_per_year", nullable = false) private Integer visitsPerYear;
    @Column(name = "annual_payment", precision = 14, scale = 2) private BigDecimal annualPayment;
    @Column(name = "full_payment", precision = 14, scale = 2) private BigDecimal fullPayment;
    @Column(name = "discount", precision = 14, scale = 2) private BigDecimal discount;
    @Column(name = "vat_percentage", precision = 5, scale = 2) private BigDecimal vatPercentage;
    @Column(name = "vat_amount", precision = 14, scale = 2) private BigDecimal vatAmount;
    @Enumerated(EnumType.STRING) @Column(name = "agreement_status", nullable = false, length = 20) private AgreementStatus agreementStatus;
    @Column(name = "is_active", nullable = false) private Boolean isActive;
    @Column(name = "installation_job_id") private Long installationJobId;
    @Column(name = "previous_agreement_id") private Long previousAgreementId;
    @Column(name = "note", columnDefinition = "text") private String note;
    @Column(name = "created_by") private Long createdBy;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @Column(name = "updated_by") private Long updatedBy;
    @Column(name = "updated_at") private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_id", insertable = false, updatable = false) private Machine machine;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "installation_job_id", insertable = false, updatable = false) private InstallationJob installationJob;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "previous_agreement_id", insertable = false, updatable = false) private MachineAgreement previousAgreement;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "created_by", insertable = false, updatable = false) private User createdByRef;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "updated_by", insertable = false, updatable = false) private User updatedByRef;
}
