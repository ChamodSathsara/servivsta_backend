package com.gestetner.servvista.Models.entity.sales;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "salesman")
public class Salesman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesman_id", nullable = false, updatable = false)
    private Long salesmanId;
    @Column(name = "user_id", unique = true)
    private Long userId;
    @Column(name = "salesman_code", nullable = false, unique = true, length = 20)
    private String salesmanCode;
    @Column(name = "salesman_name", nullable = false, length = 150)
    private String salesmanName;
    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;
    @Column(name = "email", length = 150)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "company", length = 20)
    private Company company;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private Long createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;
}
