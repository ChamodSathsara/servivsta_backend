package com.gestetner.servvista.Repositories.customers;



import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.Enums.Customers.CustomerGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Customer}.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsBySageCodeIgnoreCase(String sageCode);

    boolean existsBySageCodeIgnoreCaseAndCustomerIdNot(String sageCode, Long customerId);

    Optional<Customer> findBySageCodeIgnoreCase(String sageCode);

    @Query("""
            select customer from Customer customer
            where (:query is null
                   or (:searchById = false and
                       (lower(customer.customerName) like lower(concat('%', :query, '%'))
                        or lower(customer.sageCode) like lower(concat('%', :query, '%'))))
                   or (:searchById = true and
                       cast(customer.customerId as string) like concat('%', :query, '%')))
              and (:active is null or customer.isActive = :active)
              and (:grade is null or customer.customerGrade = :grade)
            """)
    Page<Customer> findPage(
            String query,
            boolean searchById,
            Boolean active,
            CustomerGrade grade,
            Pageable pageable);
}
