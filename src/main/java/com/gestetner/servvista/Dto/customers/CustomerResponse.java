package com.gestetner.servvista.Dto.customers;

import com.gestetner.servvista.Models.Enums.Customers.CustomerGrade;
import com.gestetner.servvista.Models.Enums.Customers.CustomerSegment;
import com.gestetner.servvista.Models.Enums.Customers.CustomerType;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long customerId,
        String sageCode,
        String customerName,
        String addressLine1,
        String addressLine2,
        String addressLine3,
        String headOfficeTelNumber,
        String headOfficeEmail,
        Boolean isActive,
        CustomerGrade customerGrade,
        CustomerType customerType,
        CustomerSegment customerSegment,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy
) {
}
