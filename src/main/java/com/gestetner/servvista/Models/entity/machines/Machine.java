package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.Enums.Machines.MachineStatus;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.sales.Dealer;
import com.gestetner.servvista.Models.entity.sales.Rep;
import com.gestetner.servvista.Models.entity.sales.Salesman;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id", nullable = false, updatable = false)
    private Long machineId;
    @Column(name = "machine_reference_number", nullable = false, unique = true, length = 20)
    private String machineReferenceNumber;
    @Column(name = "serial_number", nullable = false, unique = true, length = 100)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "company", nullable = false, length = 20)
    private Company company;
    @Enumerated(EnumType.STRING)
    @Column(name = "division", nullable = false, length = 20)
    private Division division;
    @Column(name = "model_id", nullable = false)
    private Long modelId;
    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false, length = 30)
    private MachineStatus currentStatus;
    @Column(name = "current_customer_site_id")
    private Long currentCustomerSiteId;
    @Column(name = "current_main_technician_id")
    private Long currentMainTechnicianId;
    @Column(name = "current_service_technician_id")
    private Long currentServiceTechnicianId;
    @Column(name = "machine_invoice_id")
    private Long machineInvoiceId;
    @Column(name = "dealer_id")
    private Long dealerId;
    @Column(name = "rep_id")
    private Long repId;
    @Column(name = "salesman_id")
    private Long salesmanId;
    @Column(name = "original_install_date")
    private LocalDate originalInstallDate;
    @Column(name = "note", columnDefinition = "text")
    private String note;
    @Column(name = "credit_note_number", length = 50)
    private String creditNoteNumber;
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "model_id", insertable = false, updatable = false)
    private MachineModel model;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "current_customer_site_id", insertable = false, updatable = false)
    private CustomerSite currentCustomerSite;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "current_main_technician_id", insertable = false, updatable = false)
    private Technician currentMainTechnician;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "current_service_technician_id", insertable = false, updatable = false)
    private Technician currentServiceTechnician;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_invoice_id", insertable = false, updatable = false)
    private MachineInvoice machineInvoice;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dealer_id", insertable = false, updatable = false)
    private Dealer dealer;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "rep_id", insertable = false, updatable = false)
    private Rep rep;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "salesman_id", insertable = false, updatable = false)
    private Salesman salesman;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private User updatedByRef;
}
