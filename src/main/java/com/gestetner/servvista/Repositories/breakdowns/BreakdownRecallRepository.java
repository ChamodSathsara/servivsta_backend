package com.gestetner.servvista.Repositories.breakdowns;

import com.gestetner.servvista.Models.entity.breakdowns.BreakdownRecall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link BreakdownRecall}.
 */
@Repository
public interface BreakdownRecallRepository extends JpaRepository<BreakdownRecall, Long> {
}
