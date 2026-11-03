package com.gestetner.servvista.Repositories.breakdowns;

import com.gestetner.servvista.Models.entity.breakdowns.BreakdownTechnicianAssignment;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownAssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link BreakdownTechnicianAssignment}.
 */
@Repository
public interface BreakdownTechnicianAssignmentRepository extends JpaRepository<BreakdownTechnicianAssignment, Long> {

    List<BreakdownTechnicianAssignment> findAllByBreakdownIdOrderByAssignedAtDesc(Long breakdownId);

    boolean existsByBreakdownIdAndTechnicianIdAndAssignmentStatus(
            Long breakdownId, Long technicianId, BreakdownAssignmentStatus assignmentStatus);

    void deleteAllByBreakdownId(Long breakdownId);
}
