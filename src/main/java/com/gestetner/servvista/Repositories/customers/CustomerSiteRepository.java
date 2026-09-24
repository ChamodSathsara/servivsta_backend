package com.gestetner.servvista.Repositories.customers;


import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link CustomerSite}.
 */
@Repository
public interface CustomerSiteRepository extends JpaRepository<CustomerSite, Long> {
}
