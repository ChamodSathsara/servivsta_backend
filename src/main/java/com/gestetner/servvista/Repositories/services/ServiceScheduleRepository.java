package com.gestetner.servvista.Repositories.services;

import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link ServiceSchedule}.
 */
@Repository
public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {
}
