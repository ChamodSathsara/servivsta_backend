package com.gestetner.servvista.Dto.customers;

import com.gestetner.servvista.Models.Enums.Customers.CustomerGrade;
import com.gestetner.servvista.Models.Enums.Customers.CustomerSegment;
import com.gestetner.servvista.Models.Enums.Customers.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @Size(max = 30) String sageCode,
        @NotBlank @Size(max = 200) String customerName,
        @Size(max = 150) String addressLine1,
        @Size(max = 150) String addressLine2,
        @Size(max = 150) String addressLine3,
        @Size(max = 30) String headOfficeTelNumber,
        @Email @Size(max = 150) String headOfficeEmail,
        Boolean isActive,
        @NotNull CustomerGrade customerGrade,
        @NotNull CustomerType customerType,
        @NotNull CustomerSegment customerSegment,
        @Positive Long createdBy
) {
}
