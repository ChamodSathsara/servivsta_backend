package com.gestetner.servvista.Dto.customers;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerSalesmanAssignmentResponse(
        Long assignmentId,
        Long salesmanId,
        LocalDate validFrom,
        LocalDate validTo,
        Boolean isCurrent,
        Long assignedBy,
        LocalDateTime createdAt
) {
}
