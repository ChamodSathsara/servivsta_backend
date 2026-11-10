package com.gestetner.servvista.Models.entity.sales;

import com.gestetner.servvista.Models.Enums.Sales.NewSaleWorkflowStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter @Entity
@Table(name = "new_sale_workflow")
public class NewSaleWorkflow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_sale_workflow_id") private Long newSaleWorkflowId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private NewSaleWorkflowStatus status;
    @Column(name = "finance_payload", nullable = false, columnDefinition = "longtext") private String financePayload;
    @Column(name = "machine_reference_number", unique = true, length = 20) private String machineReferenceNumber;
    @Column(name = "despatched_from_stores") private Boolean despatchedFromStores;
    @Column(name = "coordinator_payload", columnDefinition = "longtext") private String coordinatorPayload;
    @Column(name = "installation_job_id") private Long installationJobId;
    @Column(name = "machine_id") private Long machineId;
    @Column(name = "created_by", nullable = false) private Long createdBy;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @Column(name = "updated_by") private Long updatedBy;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
}
