package com.gestetner.servvista.Repositories.installations;


import com.gestetner.servvista.Models.entity.installations.InstallationSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link InstallationSubmission}.
 */
@Repository
public interface InstallationSubmissionRepository extends JpaRepository<InstallationSubmission, Long> {
}
