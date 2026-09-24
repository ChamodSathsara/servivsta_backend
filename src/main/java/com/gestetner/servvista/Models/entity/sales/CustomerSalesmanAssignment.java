package com.gestetner.servvista.Models.entity.sales;

import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "customer_salesman_assignment")
public class CustomerSalesmanAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_salesman_assignment_id", nullable = false, updatable = false)
    private Long customerSalesmanAssignmentId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "salesman_id", nullable = false)
    private Long salesmanId;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent;

    @Column(name = "assigned_by", nullable = false)
    private Long assignedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesman_id", insertable = false, updatable = false)
    private Salesman salesman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", insertable = false, updatable = false)
    private User assignedByRef;

    public CustomerSalesmanAssignment() {
    }

    public Long getCustomerSalesmanAssignmentId() {
        return customerSalesmanAssignmentId;
    }

    public void setCustomerSalesmanAssignmentId(Long customerSalesmanAssignmentId) {
        this.customerSalesmanAssignmentId = customerSalesmanAssignmentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public Boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
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

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public User getAssignedByRef() {
        return assignedByRef;
    }

    public void setAssignedByRef(User assignedByRef) {
        this.assignedByRef = assignedByRef;
    }

}
