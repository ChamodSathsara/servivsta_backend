package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.Enums.Machines.WarrantyType;
import com.gestetner.servvista.Models.Enums.Machines.WarrantyStatus;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "machine_warranty")
public class MachineWarranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_warranty_id", nullable = false, updatable = false)
    private Long machineWarrantyId;
    @Column(name = "machine_id", nullable = false)
    private Long machineId;
    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_type", nullable = false, length = 20)
    private WarrantyType warrantyType;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "duration_months")
    private Integer durationMonths;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private WarrantyStatus status;
    @Column(name = "note", columnDefinition = "text")
    private String note;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;
}
