package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineAssignment}.
 */
@Repository
public interface MachineAssignmentRepository extends JpaRepository<MachineAssignment, Long> {
}
