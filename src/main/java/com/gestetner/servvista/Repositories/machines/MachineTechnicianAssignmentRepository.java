package com.gestetner.servvista.Repositories.machines;

import com.gestetner.servvista.Models.entity.machines.MachineTechnicianAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineTechnicianAssignment}.
 */
@Repository
public interface MachineTechnicianAssignmentRepository extends JpaRepository<MachineTechnicianAssignment, Long> {
}
