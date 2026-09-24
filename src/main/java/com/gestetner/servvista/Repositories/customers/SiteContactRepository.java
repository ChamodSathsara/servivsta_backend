package com.gestetner.servvista.Repositories.customers;


import com.gestetner.servvista.Models.entity.customers.SiteContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link SiteContact}.
 */
@Repository
public interface SiteContactRepository extends JpaRepository<SiteContact, Long> {
}
