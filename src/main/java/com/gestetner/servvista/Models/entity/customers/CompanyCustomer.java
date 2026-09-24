package com.gestetner.servvista.Models.entity.customers;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_customer", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "company"}))
public class CompanyCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_customer_id", nullable = false, updatable = false)
    private Long companyCustomerId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "company", nullable = false, length = 20)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;
}
