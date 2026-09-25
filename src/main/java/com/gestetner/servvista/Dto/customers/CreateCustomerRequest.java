package com.gestetner.servvista.Dto.customers;

import com.gestetner.servvista.Models.Enums.Customers.CustomerGrade;
import com.gestetner.servvista.Models.Enums.Customers.CustomerSegment;
import com.gestetner.servvista.Models.Enums.Customers.CustomerType;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

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
        @NotNull @Positive Long createdBy,
        @NotNull @Size(min = 1, max = 2) List<@NotNull Company> companies,
        @NotNull @Positive Long salesmanId
) {
}
