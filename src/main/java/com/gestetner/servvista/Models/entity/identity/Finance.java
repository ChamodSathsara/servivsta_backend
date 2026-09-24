package com.gestetner.servvista.Models.entity.identity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "finance")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finance_id", nullable = false, updatable = false)
    private Long financeId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "finance_name", nullable = false, length = 150)
    private String financeName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
