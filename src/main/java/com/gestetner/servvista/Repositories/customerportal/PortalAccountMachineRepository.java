package com.gestetner.servvista.Repositories.customerportal;


import com.gestetner.servvista.Models.entity.customerportal.PortalAccountMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link PortalAccountMachine}.
 */
@Repository
public interface PortalAccountMachineRepository extends JpaRepository<PortalAccountMachine, Long> {
}
