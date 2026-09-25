package com.gestetner.servvista.Repositories.installations;


import com.gestetner.servvista.Models.entity.installations.InstallationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link InstallationJob}.
 */
@Repository
public interface InstallationJobRepository extends JpaRepository<InstallationJob, Long> {

    @Query(value = "select coalesce(max(cast(substring(job_number, 3) as unsigned)), 0) " +
            "from installation_job where job_number regexp '^IJ[0-9]+$'", nativeQuery = true)
    long findMaximumJobNumberSequence();

    @Query("""
            select job from InstallationJob job
            join fetch job.customer
            left join fetch job.customerSite
            left join fetch job.machineInvoice
            left join fetch job.dealer
            left join fetch job.rep
            join fetch job.assignedTechnician
            order by job.installationJobId
            """)
    List<InstallationJob> findAllWithDetails();

    @Query("""
            select job from InstallationJob job
            join fetch job.customer
            left join fetch job.customerSite
            left join fetch job.machineInvoice
            left join fetch job.dealer
            left join fetch job.rep
            join fetch job.assignedTechnician
            where job.installationJobId = :jobId
            """)
    Optional<InstallationJob> findByIdWithDetails(Long jobId);
}
