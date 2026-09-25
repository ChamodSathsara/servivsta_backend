package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.customers.CreateCustomerRequest;
import com.gestetner.servvista.Dto.customers.CustomerResponse;
import com.gestetner.servvista.Dto.customers.UpdateCustomerRequest;
import com.gestetner.servvista.Dto.customers.CustomerSalesmanAssignmentResponse;
import com.gestetner.servvista.Mapper.CustomerMapper;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.entity.customers.CompanyCustomer;
import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.entity.sales.CustomerSalesmanAssignment;
import com.gestetner.servvista.Models.entity.sales.Salesman;
import com.gestetner.servvista.Repositories.customers.CompanyCustomerRepository;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import com.gestetner.servvista.Repositories.sales.CustomerSalesmanAssignmentRepository;
import com.gestetner.servvista.Repositories.sales.SalesmanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyCustomerRepository companyCustomerRepository;
    private final CustomerSalesmanAssignmentRepository customerSalesmanAssignmentRepository;
    private final SalesmanRepository salesmanRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(
            CustomerRepository customerRepository,
            CompanyCustomerRepository companyCustomerRepository,
            CustomerSalesmanAssignmentRepository customerSalesmanAssignmentRepository,
            SalesmanRepository salesmanRepository,
            CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.companyCustomerRepository = companyCustomerRepository;
        this.customerSalesmanAssignmentRepository = customerSalesmanAssignmentRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponse create(CreateCustomerRequest request) {
        String sageCode = customerMapper.normalizeCode(request.sageCode());
        ensureSageCodeAvailable(sageCode, null);

        try {
            List<Company> companies = validateCompanies(request.companies());
            Salesman salesman = salesmanRepository.findById(request.salesmanId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Salesman " + request.salesmanId() + " was not found"));
            if (!Boolean.TRUE.equals(salesman.getIsActive())) {
                throw new IllegalArgumentException("The selected salesman is inactive");
            }
            if (salesman.getCompany() != null && !companies.contains(salesman.getCompany())) {
                throw new IllegalArgumentException(
                        "The salesman's primary company must be assigned to the customer");
            }

            LocalDateTime createdAt = LocalDateTime.now();
            Customer customer = customerMapper.toEntity(request, LocalDateTime.now());
            customer = customerRepository.saveAndFlush(customer);

            Long customerId = customer.getCustomerId();
            List<CompanyCustomer> companyAssignments = companies.stream().map(company -> {
                CompanyCustomer assignment = new CompanyCustomer();
                assignment.setCustomerId(customerId);
                assignment.setCompany(company);
                return assignment;
            }).toList();
            companyCustomerRepository.saveAllAndFlush(companyAssignments);

            CustomerSalesmanAssignment salesmanAssignment = new CustomerSalesmanAssignment();
            salesmanAssignment.setCustomerId(customerId);
            salesmanAssignment.setSalesmanId(salesman.getSalesmanId());
            salesmanAssignment.setValidFrom(LocalDate.now());
            salesmanAssignment.setIsCurrent(true);
            salesmanAssignment.setAssignedBy(request.createdBy());
            salesmanAssignment.setCreatedAt(createdAt);
            salesmanAssignment = customerSalesmanAssignmentRepository.saveAndFlush(salesmanAssignment);

            return customerMapper.toResponse(
                    customer, companies, toAssignmentResponse(salesmanAssignment));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateCustomer(exception);
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll(
                Sort.by(Sort.Direction.ASC, "customerId"));
        return buildResponses(customers);
    }

    public CustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = findCustomer(customerId);
        String sageCode = customerMapper.normalizeCode(request.sageCode());
        ensureSageCodeAvailable(sageCode, customerId);

        try {
            customerMapper.update(customer, request, LocalDateTime.now());
            customer = customerRepository.saveAndFlush(customer);
            return buildResponses(List.of(customer)).getFirst();
        } catch (DataIntegrityViolationException exception) {
            throw duplicateCustomer(exception);
        }
    }

    public void delete(Long customerId) {
        Customer customer = findCustomer(customerId);
        try {
            customerSalesmanAssignmentRepository.deleteAllByCustomerId(customerId);
            customerSalesmanAssignmentRepository.flush();
            companyCustomerRepository.deleteAllByCustomerId(customerId);
            companyCustomerRepository.flush();
            customerRepository.delete(customer);
            customerRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Customer " + customerId + " cannot be deleted because it is referenced by other records",
                    exception);
        }
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer " + customerId + " was not found"));
    }

    private void ensureSageCodeAvailable(String sageCode, Long excludedCustomerId) {
        if (sageCode == null) {
            return;
        }
        boolean exists = excludedCustomerId == null
                ? customerRepository.existsBySageCodeIgnoreCase(sageCode)
                : customerRepository.existsBySageCodeIgnoreCaseAndCustomerIdNot(
                        sageCode, excludedCustomerId);
        if (exists) {
            throw new IllegalStateException("A customer with SAGE code '" + sageCode + "' already exists");
        }
    }

    private IllegalStateException duplicateCustomer(DataIntegrityViolationException exception) {
        return new IllegalStateException(
                "A customer with the supplied unique details already exists", exception);
    }

    private List<Company> validateCompanies(List<Company> companies) {
        if (companies == null || companies.isEmpty() || companies.size() > Company.values().length) {
            throw new IllegalArgumentException("One or two companies must be supplied");
        }
        LinkedHashSet<Company> uniqueCompanies = new LinkedHashSet<>(companies);
        if (uniqueCompanies.contains(null) || uniqueCompanies.size() != companies.size()) {
            throw new IllegalArgumentException("Company assignments must be unique and non-null");
        }
        return List.copyOf(uniqueCompanies);
    }

    private List<CustomerResponse> buildResponses(List<Customer> customers) {
        if (customers.isEmpty()) {
            return List.of();
        }
        List<Long> customerIds = customers.stream().map(Customer::getCustomerId).toList();
        Map<Long, List<Company>> companiesByCustomer = companyCustomerRepository
                .findAllByCustomerIdIn(customerIds).stream()
                .collect(Collectors.groupingBy(
                        CompanyCustomer::getCustomerId,
                        Collectors.mapping(CompanyCustomer::getCompany, Collectors.toList())));
        Map<Long, CustomerSalesmanAssignment> assignmentsByCustomer =
                customerSalesmanAssignmentRepository
                        .findAllByCustomerIdInAndIsCurrentTrue(customerIds).stream()
                        .collect(Collectors.toMap(
                                CustomerSalesmanAssignment::getCustomerId,
                                Function.identity(),
                                (first, ignored) -> first));

        return customers.stream().map(customer -> customerMapper.toResponse(
                        customer,
                        companiesByCustomer.getOrDefault(customer.getCustomerId(), List.of()),
                        toAssignmentResponse(assignmentsByCustomer.get(customer.getCustomerId()))))
                .toList();
    }

    private CustomerSalesmanAssignmentResponse toAssignmentResponse(
            CustomerSalesmanAssignment assignment) {
        if (assignment == null) {
            return null;
        }
        return new CustomerSalesmanAssignmentResponse(
                assignment.getCustomerSalesmanAssignmentId(),
                assignment.getSalesmanId(),
                assignment.getValidFrom(),
                assignment.getValidTo(),
                assignment.isCurrent(),
                assignment.getAssignedBy(),
                assignment.getCreatedAt());
    }
}
