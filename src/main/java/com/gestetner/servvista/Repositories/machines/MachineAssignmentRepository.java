package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for {@link MachineAssignment}.
 */
@Repository
public interface MachineAssignmentRepository extends JpaRepository<MachineAssignment, Long> {
    List<MachineAssignment> findAllByMachineIdOrderByAssignedFromDescMachineAssignmentIdDesc(Long machineId);
    List<MachineAssignment> findAllByMachineIdAndIsCurrentTrue(Long machineId);
}
