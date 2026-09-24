package com.gestetner.servvista.Models.entity.machines;

import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "machine_type")
public class MachineType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_type_id", nullable = false, updatable = false)
    private Long machineTypeId;

    @Column(name = "machine_type_name", nullable = false)
    private String machineTypeName;

    @Column(name = "machine_type_description")
    private String machineTypeDescription;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public MachineType() {
    }

    public Long getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(Long machineTypeId) {
        this.machineTypeId = machineTypeId;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public String getMachineTypeDescription() {
        return machineTypeDescription;
    }

    public void setMachineTypeDescription(String machineTypeDescription) {
        this.machineTypeDescription = machineTypeDescription;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
