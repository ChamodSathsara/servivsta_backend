package com.gestetner.servvista.Models.entity.identity;

import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", nullable = false, updatable = false)
    private Long permissionId;

    @Column(name = "permission_code", nullable = false)
    private String permissionCode;

    @Column(name = "description")
    private String description;

    public Permission() {
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
