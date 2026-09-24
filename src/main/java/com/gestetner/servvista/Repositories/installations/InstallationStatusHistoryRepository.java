package com.gestetner.servvista.Repositories.installations;


import com.gestetner.servvista.Models.entity.installations.InstallationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link InstallationStatusHistory}.
 */
@Repository
public interface    InstallationStatusHistoryRepository extends JpaRepository<InstallationStatusHistory, Long> {
}
