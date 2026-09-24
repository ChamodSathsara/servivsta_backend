package com.gestetner.servvista.Repositories.agreements;

import com.gestetner.servvista.Models.entity.agreements.MachineAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineAgreement}.
 */
@Repository
public interface MachineAgreementRepository extends JpaRepository<MachineAgreement, Long> {
}
