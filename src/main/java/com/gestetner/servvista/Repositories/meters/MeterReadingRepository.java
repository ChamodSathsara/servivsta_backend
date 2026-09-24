package com.gestetner.servvista.Repositories.meters;

import com.gestetner.servvista.Models.entity.meters.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MeterReading}.
 */
@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {
}
