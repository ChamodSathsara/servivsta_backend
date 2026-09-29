package com.gestetner.servvista.Repositories.breakdowns;


import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Breakdown}.
 */
@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

    @Query(value = "select coalesce(max(cast(substring(breakdown_number, 3) as unsigned)), 0) " +
            "from breakdown where breakdown_number regexp '^BD[0-9]+$'", nativeQuery = true)
    long findMaximumBreakdownSequence();

    @Query("select breakdown from Breakdown breakdown join fetch breakdown.machine join fetch breakdown.customerSite order by breakdown.breakdownId desc")
    List<Breakdown> findAllWithDetails();

    @Query("select breakdown from Breakdown breakdown join fetch breakdown.machine join fetch breakdown.customerSite where breakdown.breakdownId = :breakdownId")
    Optional<Breakdown> findByIdWithDetails(Long breakdownId);
}
