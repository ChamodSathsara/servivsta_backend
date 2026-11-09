package com.gestetner.servvista.Repositories.agreements;

import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import com.gestetner.servvista.Dto.reports.MachineAgreementReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

/**
 * Spring Data JPA repository for {@link MachineAgreement}.
 */
@Repository
public interface MachineAgreementRepository extends JpaRepository<MachineAgreement, Long> {

    @Query(value = "select coalesce(max(cast(substring(agreement_number, length(:prefix) + 1) as unsigned)), 0) " +
            "from machine_agreement where agreement_number regexp concat('^', :prefix, '[0-9]+$')",
            nativeQuery = true)
    long findMaximumAgreementSequence(@Param("prefix") String prefix);

    @Query("""
            select agreement from MachineAgreement agreement
            join fetch agreement.machine
            left join fetch agreement.installationJob
            left join fetch agreement.previousAgreement
            order by agreement.agreementId
            """)
    List<MachineAgreement> findAllWithDetails();

    @Query("""
            select agreement from MachineAgreement agreement
            join fetch agreement.machine
            left join fetch agreement.installationJob
            left join fetch agreement.previousAgreement
            where agreement.agreementId = :agreementId
            """)
    Optional<MachineAgreement> findByIdWithDetails(Long agreementId);

    Optional<MachineAgreement>
            findFirstByMachineIdAndInstallationJobIdAndIsActiveTrueOrderByAgreementIdDesc(
                    Long machineId, Long installationJobId);

    @Query("""
            select new com.gestetner.servvista.Dto.reports.MachineAgreementReportRow(
                agreement.agreementId, agreement.agreementNumber,
                agreement.agreementType, agreement.agreementStatus,
                agreement.agreementStartDate, agreement.agreementEndDate,
                agreement.annualPayment, agreement.fullPayment,
                machine.machineId, machine.machineReferenceNumber, machine.serialNumber,
                model.modelNumber, model.modelName,
                customer.customerId, customer.customerName,
                site.customerSiteId, site.siteName, site.area,
                technician.technicianId, technician.technicianName, machine.division)
            from MachineAgreement agreement
            join agreement.machine machine
            left join machine.model model
            left join machine.currentCustomerSite site
            left join site.customer customer
            left join machine.currentMainTechnician technician
            where agreement.agreementType = :agreementType
              and agreement.agreementStartDate <= :lastDate
              and agreement.agreementEndDate >= :startDate
              and (:area is null or site.area = :area)
              and (:mainTechnicianId is null or technician.technicianId = :mainTechnicianId)
              and (:division is null or machine.division = :division)
            """)
    Page<MachineAgreementReportRow> findReport(
            @Param("agreementType") AgreementType agreementType,
            @Param("startDate") LocalDate startDate,
            @Param("lastDate") LocalDate lastDate,
            @Param("area") Area area,
            @Param("mainTechnicianId") Long mainTechnicianId,
            @Param("division") Division division,
            Pageable pageable);
}
