package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link MachineInvoice}.
 */
@Repository
public interface MachineInvoiceRepository extends JpaRepository<MachineInvoice, Long> {

    boolean existsByInvoiceNumberIgnoreCase(String invoiceNumber);

    boolean existsByInvoiceNumberIgnoreCaseAndMachineInvoiceIdNot(
            String invoiceNumber, Long machineInvoiceId);

    @Query("select invoice from MachineInvoice invoice join fetch invoice.customer order by invoice.machineInvoiceId")
    List<MachineInvoice> findAllWithCustomer();

    @Query("select invoice from MachineInvoice invoice join fetch invoice.customer where invoice.machineInvoiceId = :machineInvoiceId")
    Optional<MachineInvoice> findByIdWithCustomer(Long machineInvoiceId);

    Optional<MachineInvoice> findByInvoiceNumberIgnoreCase(String invoiceNumber);

    boolean existsByInvoiceNumberIgnoreCaseAndCustomerId(String invoiceNumber, Long customerId);

    boolean existsByInvoiceNumberIgnoreCaseAndCustomerIdAndMachineInvoiceIdNot(
            String invoiceNumber, Long customerId, Long machineInvoiceId);

    Optional<MachineInvoice> findByInvoiceNumberIgnoreCaseAndCustomerId(
            String invoiceNumber, Long customerId);
}
