package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineModel}.
 */
@Repository
public interface MachineModelRepository extends JpaRepository<MachineModel, Long> {
}
