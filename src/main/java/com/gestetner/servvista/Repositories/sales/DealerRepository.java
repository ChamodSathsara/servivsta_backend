package com.gestetner.servvista.Repositories.sales;

import com.gestetner.servvista.Models.entity.sales.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Dealer}.
 */
@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
}
