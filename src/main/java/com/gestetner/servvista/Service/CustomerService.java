package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.customers.CreateCustomerRequest;
import com.gestetner.servvista.Dto.customers.CustomerResponse;
import com.gestetner.servvista.Dto.customers.UpdateCustomerRequest;
import com.gestetner.servvista.Mapper.CustomerMapper;
import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponse create(CreateCustomerRequest request) {
        String sageCode = customerMapper.normalizeCode(request.sageCode());
        ensureSageCodeAvailable(sageCode, null);

        try {
            Customer customer = customerMapper.toEntity(request, LocalDateTime.now());
            return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateCustomer(exception);
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll(Sort.by(Sort.Direction.ASC, "customerId")).stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    public CustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = findCustomer(customerId);
        String sageCode = customerMapper.normalizeCode(request.sageCode());
        ensureSageCodeAvailable(sageCode, customerId);

        try {
            customerMapper.update(customer, request, LocalDateTime.now());
            return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateCustomer(exception);
        }
    }

    public void delete(Long customerId) {
        Customer customer = findCustomer(customerId);
        try {
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
}
