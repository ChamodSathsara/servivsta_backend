package com.gestetner.servvista.Models.entity.installations;

import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import com.gestetner.servvista.Models.entity.sales.Dealer;
import com.gestetner.servvista.Models.entity.sales.Rep;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "installation_job")
public class InstallationJob {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "installation_job_id", nullable = false, updatable = false)
    private Long installationJobId;
    @Column(name = "job_number", nullable = false, unique = true, length = 30)
    private String jobNumber;
    @Enumerated(EnumType.STRING) @Column(name = "company", nullable = false, length = 20)
    private Company company;
    @Enumerated(EnumType.STRING) @Column(name = "division", nullable = false, length = 20)
    private Division division;
    @Column(name = "customer_id", nullable = false) private Long customerId;
    @Column(name = "customer_site_id") private Long customerSiteId;
    @Column(name = "machine_invoice_id") private Long machineInvoiceId;
    @Column(name = "dealer_id") private Long dealerId;
    @Column(name = "rep_id") private Long repId;
    @Column(name = "assigned_technician_id", nullable = false) private Long assignedTechnicianId;
    @Column(name = "expected_install_date") private LocalDate expectedInstallDate;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false, length = 20)
    private InstallationJobStatus status;
    @Column(name = "created_by") private Long createdBy;
    @Column(name = "created_at") private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id", insertable = false, updatable = false) private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_site_id", insertable = false, updatable = false) private CustomerSite customerSite;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_invoice_id", insertable = false, updatable = false) private MachineInvoice machineInvoice;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dealer_id", insertable = false, updatable = false) private Dealer dealer;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "rep_id", insertable = false, updatable = false) private Rep rep;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "assigned_technician_id", insertable = false, updatable = false) private Technician assignedTechnician;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "created_by", insertable = false, updatable = false) private User createdByRef;
}
