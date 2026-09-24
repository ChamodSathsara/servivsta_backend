package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineInvoice}.
 */
@Repository
public interface MachineInvoiceRepository extends JpaRepository<MachineInvoice, Long> {
}
