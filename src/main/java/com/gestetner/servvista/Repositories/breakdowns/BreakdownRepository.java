package com.gestetner.servvista.Repositories.breakdowns;


import com.gestetner.servvista.Dto.reports.BreakdownJobReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.entity.breakdowns.Breakdown;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

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

    @Query("""
            select new com.gestetner.servvista.Dto.reports.BreakdownJobReportRow(
                breakdown.breakdownId, breakdown.breakdownNumber,
                machine.machineReferenceNumber, machine.serialNumber,
                machine.company, machine.division,
                customer.customerName, site.siteName, site.area,
                agreement.agreementType, agreement.agreementStatus,
                breakdown.status, technician.techCode, technician.technicianName,
                breakdown.reportedNote, breakdown.solutionNote, breakdown.contactEmail,
                breakdown.expectedCompletionAt, breakdown.createdAt,
                breakdown.startedAt, breakdown.completedAt)
            from Breakdown breakdown
            join breakdown.machine machine
            join breakdown.customerSite site
            left join site.customer customer
            left join MachineAgreement agreement
                on agreement.machineId = machine.machineId and agreement.isActive = true
            left join BreakdownTechnicianAssignment assignment
                on assignment.breakdownId = breakdown.breakdownId
                and assignment.assignmentStatus = com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownAssignmentStatus.CURRENT
            left join assignment.technician technician
            where (:company is null or machine.company = :company)
              and breakdown.createdAt >= :fromDateTime
              and breakdown.createdAt < :toDateTime
              and (:division is null or machine.division = :division)
              and (:area is null or site.area = :area)
              and (:agreementType is null or agreement.agreementType = :agreementType)
              and (:jobStatus is null or breakdown.status = :jobStatus)
              and (:search is null
                   or lower(machine.machineReferenceNumber) like lower(concat('%', :search, '%'))
                   or lower(technician.techCode) like lower(concat('%', :search, '%')))
            """)
    Page<BreakdownJobReportRow> findBreakdownReport(
            LocalDateTime fromDateTime,
            LocalDateTime toDateTime,
            Company company,
            Division division,
            Area area,
            AgreementType agreementType,
            BreakdownStatus jobStatus,
            String search,
            Pageable pageable);
}
