package com.gestetner.servvista.Models.entity.machines;

import com.gestetner.servvista.Models.Enums.Machines.TechnicianAssignmentRole;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "machine_technician_assignment")
public class MachineTechnicianAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_technician_assignment_id", nullable = false, updatable = false)
    private Long machineTechnicianAssignmentId;
    @Column(name = "machine_id", nullable = false)
    private Long machineId;
    @Column(name = "technician_id", nullable = false)
    private Long technicianId;
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_role", nullable = false, length = 20)
    private TechnicianAssignmentRole assignmentRole;
    @Column(name = "assigned_from", nullable = false)
    private LocalDate assignedFrom;
    @Column(name = "assigned_to")
    private LocalDate assignedTo;
    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent;
    @Column(name = "assigned_by")
    private Long assignedBy;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "machine_id", insertable = false, updatable = false)
    private Machine machine;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "technician_id", insertable = false, updatable = false)
    private Technician technician;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "assigned_by", insertable = false, updatable = false)
    private User assignedByRef;
}
