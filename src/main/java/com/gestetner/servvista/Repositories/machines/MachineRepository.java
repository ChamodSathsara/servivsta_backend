package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Machine}.
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
}
