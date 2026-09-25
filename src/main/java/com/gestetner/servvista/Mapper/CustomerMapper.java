package com.gestetner.servvista.Mapper;

import com.gestetner.servvista.Dto.customers.CreateCustomerRequest;
import com.gestetner.servvista.Dto.customers.CustomerResponse;
import com.gestetner.servvista.Dto.customers.UpdateCustomerRequest;
import com.gestetner.servvista.Dto.customers.CustomerSalesmanAssignmentResponse;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.entity.customers.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.List;

@Component
public class CustomerMapper {

    public Customer toEntity(CreateCustomerRequest request, LocalDateTime createdAt) {
        Customer customer = new Customer();
        customer.setSageCode(normalizeCode(request.sageCode()));
        customer.setCustomerName(request.customerName().trim());
        customer.setAddressLine1(normalizeOptional(request.addressLine1()));
        customer.setAddressLine2(normalizeOptional(request.addressLine2()));
        customer.setAddressLine3(normalizeOptional(request.addressLine3()));
        customer.setHeadOfficeTelNumber(normalizeOptional(request.headOfficeTelNumber()));
        customer.setHeadOfficeEmail(normalizeEmail(request.headOfficeEmail()));
        customer.setIsActive(request.isActive() == null || request.isActive());
        customer.setCustomerGrade(request.customerGrade());
        customer.setCustomerType(request.customerType());
        customer.setCustomerSegment(request.customerSegment());
        customer.setCreatedAt(createdAt);
        customer.setCreatedBy(request.createdBy());
        return customer;
    }

    public void update(Customer customer, UpdateCustomerRequest request, LocalDateTime updatedAt) {
        customer.setSageCode(normalizeCode(request.sageCode()));
        customer.setCustomerName(request.customerName().trim());
        customer.setAddressLine1(normalizeOptional(request.addressLine1()));
        customer.setAddressLine2(normalizeOptional(request.addressLine2()));
        customer.setAddressLine3(normalizeOptional(request.addressLine3()));
        customer.setHeadOfficeTelNumber(normalizeOptional(request.headOfficeTelNumber()));
        customer.setHeadOfficeEmail(normalizeEmail(request.headOfficeEmail()));
        customer.setIsActive(request.isActive());
        customer.setCustomerGrade(request.customerGrade());
        customer.setCustomerType(request.customerType());
        customer.setCustomerSegment(request.customerSegment());
        customer.setUpdatedAt(updatedAt);
        customer.setUpdatedBy(request.updatedBy());
    }

    public CustomerResponse toResponse(Customer customer) {
        return toResponse(customer, List.of(), null);
    }

    public CustomerResponse toResponse(
            Customer customer,
            List<Company> companies,
            CustomerSalesmanAssignmentResponse salesmanAssignment) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getSageCode(),
                customer.getCustomerName(),
                customer.getAddressLine1(),
                customer.getAddressLine2(),
                customer.getAddressLine3(),
                customer.getHeadOfficeTelNumber(),
                customer.getHeadOfficeEmail(),
                customer.getIsActive(),
                customer.getCustomerGrade(),
                customer.getCustomerType(),
                customer.getCustomerSegment(),
                customer.getCreatedAt(),
                customer.getCreatedBy(),
                customer.getUpdatedAt(),
                customer.getUpdatedBy(),
                List.copyOf(companies),
                salesmanAssignment);
    }

    public String normalizeCode(String value) {
        String normalized = normalizeOptional(value);
        return normalized == null ? null : normalized.toUpperCase(Locale.ROOT);
    }

    private String normalizeEmail(String value) {
        String normalized = normalizeOptional(value);
        return normalized == null ? null : normalized.toLowerCase(Locale.ROOT);
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
