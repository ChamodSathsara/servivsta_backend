package com.gestetner.servvista.Models.entity.identity;

import com.gestetner.servvista.Models.Enums.Identity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "role_permission", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "permission_id"}))
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_permission_id", nullable = false, updatable = false)
    private Long rolePermissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 40)
    private Role role;

    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private Permission permission;
}
