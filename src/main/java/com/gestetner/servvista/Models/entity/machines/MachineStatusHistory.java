package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.Enums.Machines.MachineStatus;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "machine_status_history")
public class MachineStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_status_history_id", nullable = false, updatable = false)
    private Long machineStatusHistoryId;
    @Column(name = "machine_id", nullable = false)
    private Long machineId;
    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", nullable = false, length = 30)
    private MachineStatus previousStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 30)
    private MachineStatus newStatus;
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
    @Column(name = "changed_by")
    private Long changedBy;
    @Column(name = "reason", length = 255)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "changed_by", insertable = false, updatable = false)
    private User changedByRef;
}
