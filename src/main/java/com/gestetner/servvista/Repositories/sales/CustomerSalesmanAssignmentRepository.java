package com.gestetner.servvista.Repositories.sales;

import com.gestetner.servvista.Models.entity.sales.CustomerSalesmanAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for {@link CustomerSalesmanAssignment}.
 */
@Repository
public interface CustomerSalesmanAssignmentRepository extends JpaRepository<CustomerSalesmanAssignment, Long> {
    List<CustomerSalesmanAssignment> findAllByCustomerIdInAndIsCurrentTrue(Collection<Long> customerIds);

    void deleteAllByCustomerId(Long customerId);
}
