package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.technician.JobCustomerDetailsResponse;
import com.gestetner.servvista.Dto.technician.JobPartyDetailsResponse;
import com.gestetner.servvista.Dto.technician.JobSiteContactResponse;
import com.gestetner.servvista.Dto.technician.JobSiteDetailsResponse;
import com.gestetner.servvista.Models.entity.customers.Customer;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.customers.SiteContact;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import com.gestetner.servvista.Repositories.customers.CustomerSiteRepository;
import com.gestetner.servvista.Repositories.customers.SiteContactRepository;
import com.gestetner.servvista.Repositories.organization.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class JobPartyDetailsService {
    private final CustomerRepository customers;
    private final CustomerSiteRepository sites;
    private final SiteContactRepository contacts;
    private final CityRepository cities;

    public JobPartyDetailsService(CustomerRepository customers,
                                  CustomerSiteRepository sites,
                                  SiteContactRepository contacts,
                                  CityRepository cities) {
        this.customers = customers;
        this.sites = sites;
        this.contacts = contacts;
        this.cities = cities;
    }

    public JobPartyDetailsResponse forSite(Long siteId) {
        if (siteId == null) return null;
        CustomerSite site = sites.findById(siteId)
                .orElseThrow(() -> new EntityNotFoundException("Customer site " + siteId + " was not found"));
        Customer customer = customers.findById(site.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + site.getCustomerId() + " was not found"));
        String city = cities.findById(site.getCityId()).map(value -> value.getCityName()).orElse(null);
        SiteContact contact = contacts.findAllByCustomerSiteId(siteId).stream()
                .filter(value -> Boolean.TRUE.equals(value.isActive()))
                .sorted(java.util.Comparator
                        .comparing((SiteContact value) -> !Boolean.TRUE.equals(value.isPrimary()))
                        .thenComparing(SiteContact::getSiteContactId))
                .findFirst().orElse(null);
        return new JobPartyDetailsResponse(
                new JobCustomerDetailsResponse(customer.getCustomerId(), customer.getSageCode(),
                        customer.getCustomerName(), address(customer.getAddressLine1(), customer.getAddressLine2(), customer.getAddressLine3()),
                        customer.getHeadOfficeTelNumber(), customer.getHeadOfficeEmail(),
                        customer.getCustomerGrade().name(), customer.getCustomerType().name(), customer.getCustomerSegment().name()),
                new JobSiteDetailsResponse(site.getCustomerSiteId(), site.getSiteName(),
                        address(site.getAddressLine1(), site.getAddressLine2(), site.getAddressLine3()),
                        site.getArea().name(), city, site.getLatitude(), site.getLongitude(), site.getIsHeadOffice()),
                contact == null ? null : new JobSiteContactResponse(contact.getSiteContactId(),
                        contact.getContactName(), contact.getDesignation(), contact.getMobileNumber(), contact.getEmail()));
    }

    private String address(String... lines) {
        return Stream.of(lines).filter(value -> value != null && !value.isBlank())
                .collect(Collectors.joining(", "));
    }
}
