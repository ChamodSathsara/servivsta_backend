package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "machine_model", uniqueConstraints = @UniqueConstraint(columnNames = {"company", "model_number"}))
public class MachineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id", nullable = false, updatable = false)
    private Long modelId;
    @Enumerated(EnumType.STRING)
    @Column(name = "company", nullable = false, length = 20)
    private Company company;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "machine_model_company", joinColumns = @JoinColumn(name = "model_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "company", nullable = false, length = 20)
    private Set<Company> companies = new LinkedHashSet<>();
    @Column(name = "manufacturer_id", nullable = false)
    private Long manufacturerId;
    @Column(name = "machine_type_id", nullable = false)
    private Long machineTypeId;
    @Column(name = "model_number", nullable = false, length = 60)
    private String modelNumber;
    @Column(name = "model_name", nullable = false, length = 150)
    private String modelName;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", insertable = false, updatable = false)
    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_type_id", insertable = false, updatable = false)
    private MachineType machineType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;
}
