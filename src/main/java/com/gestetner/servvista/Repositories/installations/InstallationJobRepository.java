package com.gestetner.servvista.Repositories.installations;


import com.gestetner.servvista.Models.entity.installations.InstallationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link InstallationJob}.
 */
@Repository
public interface InstallationJobRepository extends JpaRepository<InstallationJob, Long> {
}
