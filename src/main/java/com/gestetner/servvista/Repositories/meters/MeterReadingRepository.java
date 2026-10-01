package com.gestetner.servvista.Repositories.meters;

import com.gestetner.servvista.Models.entity.meters.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link MeterReading}.
 */
@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    @Query("""
            select reading from MeterReading reading
            join fetch reading.meterCounterType
            where reading.machineId = :machineId
            order by reading.readingDatetime desc, reading.meterReadingId desc
            """)
    List<MeterReading> findAllByMachineIdWithCounterTypeOrderByReadingDatetimeDesc(Long machineId);
}
