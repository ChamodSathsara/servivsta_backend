package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "machine_invoice")
public class MachineInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_invoice_id", nullable = false, updatable = false)
    private Long machineInvoiceId;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "belita_invoice_number")
    private String belitaInvoiceNumber;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "note")
    private String note;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;

    public MachineInvoice() {
    }

    public Long getMachineInvoiceId() {
        return machineInvoiceId;
    }

    public void setMachineInvoiceId(Long machineInvoiceId) {
        this.machineInvoiceId = machineInvoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBelitaInvoiceNumber() {
        return belitaInvoiceNumber;
    }

    public void setBelitaInvoiceNumber(String belitaInvoiceNumber) {
        this.belitaInvoiceNumber = belitaInvoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(User createdByRef) {
        this.createdByRef = createdByRef;
    }

}
