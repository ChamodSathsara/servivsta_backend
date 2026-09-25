package com.gestetner.servvista.Repositories.agreements;

import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
}
