package com.gestetner.servvista.Models.entity.customers;

import com.gestetner.servvista.Models.Enums.Customers.CustomerGrade;
import com.gestetner.servvista.Models.Enums.Customers.CustomerSegment;
import com.gestetner.servvista.Models.Enums.Customers.CustomerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false, updatable = false)
    private Long customerId;

    @Column(name = "sage_code", unique = true, length = 30)
    private String sageCode;

    @Column(name = "customer_name", nullable = false, length = 200)
    private String customerName;

    @Column(name = "address_line_1", length = 150)
    private String addressLine1;
    @Column(name = "address_line_2", length = 150)
    private String addressLine2;
    @Column(name = "address_line_3", length = 150)
    private String addressLine3;
    @Column(name = "head_office_tel_number", length = 30)
    private String headOfficeTelNumber;
    @Column(name = "head_office_email", length = 150)
    private String headOfficeEmail;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_grade", nullable = false, length = 20)
    private CustomerGrade customerGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false, length = 30)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_segment", nullable = false, length = 50)
    private CustomerSegment customerSegment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private Long updatedBy;
}
