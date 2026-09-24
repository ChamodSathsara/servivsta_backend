package com.gestetner.servvista.Repositories.breakdowns;

import com.gestetner.servvista.Models.entity.breakdowns.BreakdownTechnicianAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link BreakdownTechnicianAssignment}.
 */
@Repository
public interface BreakdownTechnicianAssignmentRepository extends JpaRepository<BreakdownTechnicianAssignment, Long> {
}
