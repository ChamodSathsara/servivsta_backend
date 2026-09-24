package com.gestetner.servvista.Repositories.machines;

import com.gestetner.servvista.Models.entity.machines.MachineWarranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineWarranty}.
 */
@Repository
public interface MachineWarrantyRepository extends JpaRepository<MachineWarranty, Long> {
}
