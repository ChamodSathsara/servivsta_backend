package com.gestetner.servvista.Repositories.services;

import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link ServiceSchedule}.
 */
@Repository
public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {

    boolean existsByAgreementId(Long agreementId);

    @Query("""
            select schedule from ServiceSchedule schedule
            join fetch schedule.agreement
            join fetch schedule.machine
            left join fetch schedule.assignedTechnician
            where schedule.serviceScheduleId = :scheduleId
            """)
    Optional<ServiceSchedule> findByIdWithDetails(Long scheduleId);

    @Query("""
            select schedule from ServiceSchedule schedule
            join fetch schedule.agreement
            join fetch schedule.machine
            left join fetch schedule.assignedTechnician
            order by schedule.expectedVisitDate, schedule.serviceScheduleId
            """)
    List<ServiceSchedule> findAllWithDetails();

    @Query("""
            select schedule from ServiceSchedule schedule
            join fetch schedule.agreement
            join fetch schedule.machine
            left join fetch schedule.assignedTechnician
            where schedule.agreementId = :agreementId
            order by schedule.agreementYearNumber, schedule.visitNumber
            """)
    List<ServiceSchedule> findAllByAgreementIdWithDetails(Long agreementId);

    @Query("""
            select schedule from ServiceSchedule schedule
            join fetch schedule.agreement
            join fetch schedule.machine
            left join fetch schedule.assignedTechnician
            where schedule.machineId = :machineId
            order by schedule.expectedVisitDate, schedule.serviceScheduleId
            """)
    List<ServiceSchedule> findAllByMachineIdWithDetails(Long machineId);
}
