package com.gestetner.servvista.Repositories.estimates;


import com.gestetner.servvista.Models.entity.estimates.EstimateAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link EstimateAcceptance}.
 */
@Repository
public interface EstimateAcceptanceRepository extends JpaRepository<EstimateAcceptance, Long> {
}
