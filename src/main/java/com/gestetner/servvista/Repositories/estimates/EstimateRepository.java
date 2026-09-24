package com.gestetner.servvista.Repositories.estimates;


import com.gestetner.servvista.Models.entity.estimates.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Estimate}.
 */
@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
}
