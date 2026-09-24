package com.gestetner.servvista.Repositories.estimates;


import com.gestetner.servvista.Models.entity.estimates.EstimateLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link EstimateLine}.
 */
@Repository
public interface EstimateLineRepository extends JpaRepository<EstimateLine, Long> {
}
