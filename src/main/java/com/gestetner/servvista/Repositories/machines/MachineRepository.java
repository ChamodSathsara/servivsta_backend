package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Collection;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Machine}.
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    boolean existsByMachineReferenceNumberIgnoreCase(String machineReferenceNumber);

    boolean existsBySerialNumberIgnoreCase(String serialNumber);

    boolean existsBySerialNumberIgnoreCaseAndMachineIdNot(String serialNumber, Long machineId);

    Optional<Machine> findByMachineReferenceNumberIgnoreCase(String machineReferenceNumber);

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

    @Query(value = """
            select machine from Machine machine
            left join fetch machine.model
            left join fetch machine.machineInvoice
            left join fetch machine.currentCustomerSite
            where (:query is null
                   or lower(machine.machineReferenceNumber) like lower(concat('%', :query, '%'))
                   or lower(machine.serialNumber) like lower(concat('%', :query, '%')))
            """, countQuery = """
            select count(machine) from Machine machine
            where (:query is null
                   or lower(machine.machineReferenceNumber) like lower(concat('%', :query, '%'))
                   or lower(machine.serialNumber) like lower(concat('%', :query, '%')))
            """)
    Page<Machine> findPageWithDetails(String query, Pageable pageable);

    @Query("""
            select machine from Machine machine
            left join fetch machine.model
            left join fetch machine.machineInvoice
            join fetch machine.currentCustomerSite site
            where site.customerId = :customerId
            order by machine.machineReferenceNumber
            """)
    List<Machine> findAllByCustomerIdWithDetails(Long customerId);

    @Query("""
            select site.customerId, count(machine)
            from Machine machine
            join machine.currentCustomerSite site
            where site.customerId in :customerIds
            group by site.customerId
            """)
    List<Object[]> countByCustomerIds(Collection<Long> customerIds);

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
