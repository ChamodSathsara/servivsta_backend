package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Machine}.
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    boolean existsBySerialNumberIgnoreCase(String serialNumber);

    boolean existsBySerialNumberIgnoreCaseAndMachineIdNot(String serialNumber, Long machineId);

    @Query(value = "select coalesce(max(cast(substring(machine_reference_number, 2) as unsigned)), 0) " +
            "from machine where machine_reference_number regexp '^Q[0-9]+$'", nativeQuery = true)
    long findMaximumReferenceSequence();

    @Query("""
            select machine from Machine machine
            left join fetch machine.model
            left join fetch machine.machineInvoice
            left join fetch machine.currentCustomerSite
            order by machine.machineId
            """)
    List<Machine> findAllWithDetails();

    @Query("""
            select machine from Machine machine
            left join fetch machine.model
            left join fetch machine.machineInvoice
            left join fetch machine.currentCustomerSite
            where machine.machineId = :machineId
            """)
    Optional<Machine> findByIdWithDetails(Long machineId);

    @Query("""
            select distinct machine from Machine machine
            left join fetch machine.model model
            left join fetch machine.machineInvoice invoice
            left join fetch machine.currentCustomerSite site
            where lower(machine.machineReferenceNumber) like lower(concat('%', :query, '%'))
               or lower(machine.serialNumber) like lower(concat('%', :query, '%'))
               or lower(model.modelNumber) like lower(concat('%', :query, '%'))
               or lower(model.modelName) like lower(concat('%', :query, '%'))
               or lower(invoice.invoiceNumber) like lower(concat('%', :query, '%'))
               or lower(site.siteName) like lower(concat('%', :query, '%'))
               or exists (
                    select contact.siteContactId from SiteContact contact
                    where contact.customerSiteId = machine.currentCustomerSiteId
                      and (lower(contact.contactName) like lower(concat('%', :query, '%'))
                           or lower(contact.email) like lower(concat('%', :query, '%')))
               )
            order by machine.machineId
            """)
    List<Machine> search(String query);
}
