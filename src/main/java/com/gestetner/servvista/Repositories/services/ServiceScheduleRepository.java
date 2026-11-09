package com.gestetner.servvista.Repositories.services;

import com.gestetner.servvista.Dto.reports.ServiceVisitReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import com.gestetner.servvista.Models.entity.services.ServiceSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link ServiceSchedule}.
 */
@Repository
public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update ServiceSchedule schedule
            set schedule.status = com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus.DUE
            where schedule.status = com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus.SCHEDULED
              and schedule.expectedVisitDate < :today
            """)
    int markScheduledVisitsDue(LocalDate today);

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

    @Query("""
            select new com.gestetner.servvista.Dto.reports.ServiceVisitReportRow(
                schedule.serviceScheduleId, machine.machineReferenceNumber, machine.serialNumber,
                machine.company, machine.division, customer.customerName, site.siteName, site.area,
                agreement.agreementNumber, agreement.agreementType, agreement.agreementStatus,
                schedule.agreementYearNumber, schedule.visitNumber, schedule.status,
                technician.techCode, technician.technicianName,
                schedule.expectedVisitDate, schedule.scheduledDate, schedule.actualVisitDate,
                schedule.startNote, solution.solutionCode, solution.solutionDescription,
                schedule.solutionNote, schedule.createdAt, schedule.startedAt, schedule.completedAt)
            from ServiceSchedule schedule
            join schedule.machine machine
            join schedule.agreement agreement
            left join machine.currentCustomerSite site
            left join site.customer customer
            left join schedule.assignedTechnician technician
            left join schedule.solutionType solution
            where (:company is null or machine.company = :company)
              and schedule.expectedVisitDate between :startDate and :endDate
              and (:division is null or machine.division = :division)
              and (:area is null or site.area = :area)
              and (:agreementType is null or agreement.agreementType = :agreementType)
              and (:visitStatus is null or schedule.status = :visitStatus)
              and (:search is null
                   or lower(machine.machineReferenceNumber) like lower(concat('%', :search, '%'))
                   or lower(technician.techCode) like lower(concat('%', :search, '%')))
            """)
    Page<ServiceVisitReportRow> findServiceVisitReport(
            LocalDate startDate,
            LocalDate endDate,
            Company company,
            Division division,
            Area area,
            AgreementType agreementType,
            ServiceScheduleStatus visitStatus,
            String search,
            Pageable pageable);
}
