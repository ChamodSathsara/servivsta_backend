package com.gestetner.servvista.Repositories.machines;

import com.gestetner.servvista.Models.entity.machines.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineType}.
 */
@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, Long> {
}
