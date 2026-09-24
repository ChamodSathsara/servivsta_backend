package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineStatusHistory}.
 */
@Repository
public interface MachineStatusHistoryRepository extends JpaRepository<MachineStatusHistory, Long> {
}
