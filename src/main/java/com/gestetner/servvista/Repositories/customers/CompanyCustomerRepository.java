package com.gestetner.servvista.Repositories.customers;


import com.gestetner.servvista.Models.entity.customers.CompanyCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for {@link CompanyCustomer}.
 */
@Repository
public interface CompanyCustomerRepository extends JpaRepository<CompanyCustomer, Long> {
    List<CompanyCustomer> findAllByCustomerIdIn(Collection<Long> customerIds);

    void deleteAllByCustomerId(Long customerId);
}
