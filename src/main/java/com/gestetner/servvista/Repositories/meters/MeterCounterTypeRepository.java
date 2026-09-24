package com.gestetner.servvista.Repositories.meters;

import com.gestetner.servvista.Models.entity.meters.MeterCounterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MeterCounterType}.
 */
@Repository
public interface MeterCounterTypeRepository extends JpaRepository<MeterCounterType, Long> {
}
