package com.gestetner.servvista.Repositories.customers;



import com.gestetner.servvista.Models.entity.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Customer}.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsBySageCodeIgnoreCase(String sageCode);

    boolean existsBySageCodeIgnoreCaseAndCustomerIdNot(String sageCode, Long customerId);
}
