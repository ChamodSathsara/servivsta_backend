package com.gestetner.servvista.Repositories.estimates;


import com.gestetner.servvista.Models.entity.estimates.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Estimate}.
 */
@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    @Query(value = "select coalesce(max(cast(substring(estimate_number, 3) as unsigned)),0) from estimate where estimate_number regexp '^ES[0-9]+$'", nativeQuery = true)
    long findMaximumEstimateSequence();
    @Query("select e from Estimate e join fetch e.machine join fetch e.technician order by e.estimateId desc")
    List<Estimate> findAllWithDetails();
    @Query("select e from Estimate e join fetch e.machine join fetch e.technician where e.estimateId=:estimateId")
    Optional<Estimate> findByIdWithDetails(Long estimateId);
}
