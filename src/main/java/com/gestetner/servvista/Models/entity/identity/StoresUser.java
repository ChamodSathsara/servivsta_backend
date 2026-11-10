package com.gestetner.servvista.Models.entity.identity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stores_user")
public class StoresUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stores_user_id", nullable = false, updatable = false)
    private Long storesUserId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "stores_user_name", nullable = false, length = 150)
    private String storesUserName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
