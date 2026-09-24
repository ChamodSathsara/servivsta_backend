package com.gestetner.servvista.Repositories.sales;

import com.gestetner.servvista.Models.entity.sales.CustomerSalesmanAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link CustomerSalesmanAssignment}.
 */
@Repository
public interface CustomerSalesmanAssignmentRepository extends JpaRepository<CustomerSalesmanAssignment, Long> {
}
