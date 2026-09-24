package com.gestetner.servvista.Repositories.customerportal;


import com.gestetner.servvista.Models.entity.customerportal.CustomerPortalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link CustomerPortalAccount}.
 */
@Repository
public interface CustomerPortalAccountRepository extends JpaRepository<CustomerPortalAccount, Long> {
}
