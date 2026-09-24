package com.gestetner.servvista.Repositories.estimates;


import com.gestetner.servvista.Models.entity.estimates.EstimateStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link EstimateStatusHistory}.
 */
@Repository
public interface EstimateStatusHistoryRepository extends JpaRepository<EstimateStatusHistory, Long> {
}
