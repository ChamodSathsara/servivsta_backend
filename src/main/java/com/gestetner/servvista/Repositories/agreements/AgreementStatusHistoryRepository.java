package com.gestetner.servvista.Repositories.agreements;

import com.gestetner.servvista.Models.entity.agreements.AgreementStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link AgreementStatusHistory}.
 */
@Repository
public interface AgreementStatusHistoryRepository extends JpaRepository<AgreementStatusHistory, Long> {
}
