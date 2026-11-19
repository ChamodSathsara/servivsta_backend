package com.gestetner.servvista.Repositories.customers;


import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link CustomerSite}.
 */
@Repository
public interface CustomerSiteRepository extends JpaRepository<CustomerSite, Long> {
    List<CustomerSite> findAllByCustomerIdAndIsActiveTrueOrderBySiteName(Long customerId);

    boolean existsByCustomerIdAndSiteNameIgnoreCase(Long customerId, String siteName);

    boolean existsByCustomerIdAndSiteNameIgnoreCaseAndCustomerSiteIdNot(
            Long customerId, String siteName, Long customerSiteId);

    Optional<CustomerSite> findByCustomerIdAndSiteNameIgnoreCase(Long customerId, String siteName);
}
