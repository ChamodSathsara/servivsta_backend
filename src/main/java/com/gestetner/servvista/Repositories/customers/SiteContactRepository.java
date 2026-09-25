package com.gestetner.servvista.Repositories.customers;


import com.gestetner.servvista.Models.entity.customers.SiteContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link SiteContact}.
 */
@Repository
public interface SiteContactRepository extends JpaRepository<SiteContact, Long> {

    boolean existsByCustomerSiteIdAndEmailIgnoreCase(Long customerSiteId, String email);

    boolean existsByCustomerSiteIdAndEmailIgnoreCaseAndSiteContactIdNot(
            Long customerSiteId, String email, Long siteContactId);

    Optional<SiteContact> findFirstByCustomerSiteIdOrderByIsPrimaryDescSiteContactIdAsc(Long customerSiteId);

    List<SiteContact> findAllByCustomerSiteId(Long customerSiteId);
}
