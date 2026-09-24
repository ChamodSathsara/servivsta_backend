package com.gestetner.servvista.Models.entity.identity;

import com.gestetner.servvista.Models.Enums.Identity.Role;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 150)
    private String userName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "division", length = 20)
    private Division division;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false, length = 20)
    private Area area;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 40)
    private Role role;
}
