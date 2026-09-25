package com.gestetner.servvista.Repositories.installations;


import com.gestetner.servvista.Models.entity.installations.InstallationSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link InstallationSubmission}.
 */
@Repository
public interface InstallationSubmissionRepository extends JpaRepository<InstallationSubmission, Long> {

    boolean existsByInstallationJobId(Long installationJobId);

    @Query("""
            select submission from InstallationSubmission submission
            join fetch submission.installationJob
            join fetch submission.machine
            join fetch submission.model
            join fetch submission.customerSite
            join fetch submission.siteContact
            order by submission.installationSubmissionId
            """)
    List<InstallationSubmission> findAllWithDetails();

    @Query("""
            select submission from InstallationSubmission submission
            join fetch submission.installationJob
            join fetch submission.machine
            join fetch submission.model
            join fetch submission.customerSite
            join fetch submission.siteContact
            where submission.installationSubmissionId = :submissionId
            """)
    Optional<InstallationSubmission> findByIdWithDetails(Long submissionId);
}
