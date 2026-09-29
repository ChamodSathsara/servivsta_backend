package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link MachineStatusHistory}.
 */
@Repository
public interface MachineStatusHistoryRepository extends JpaRepository<MachineStatusHistory, Long> {

    List<MachineStatusHistory> findAllByOrderByChangedAtDesc();

    List<MachineStatusHistory> findAllByMachineIdOrderByChangedAtDesc(Long machineId);

    void deleteAllByMachineId(Long machineId);
}
