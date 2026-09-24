package com.gestetner.servvista.Repositories.breakdowns;


import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Breakdown}.
 */
@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {
}
