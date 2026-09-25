package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.machines.CustomerSiteData;
import com.gestetner.servvista.Dto.machines.MachineData;
import com.gestetner.servvista.Dto.machines.MachineInvoiceData;
import com.gestetner.servvista.Dto.machines.MachineRequest;
import com.gestetner.servvista.Dto.machines.MachineResponse;
import com.gestetner.servvista.Dto.machines.SiteContactData;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.customers.SiteContact;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import com.gestetner.servvista.Models.entity.machines.MachineModel;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import com.gestetner.servvista.Repositories.customers.CustomerSiteRepository;
import com.gestetner.servvista.Repositories.customers.SiteContactRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineInvoiceRepository;
import com.gestetner.servvista.Repositories.machines.MachineModelRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.organization.CityRepository;
import com.gestetner.servvista.Repositories.sales.DealerRepository;
import com.gestetner.servvista.Repositories.sales.RepRepository;
import com.gestetner.servvista.Repositories.sales.SalesmanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineModelRepository machineModelRepository;
    private final MachineInvoiceRepository machineInvoiceRepository;
    private final CustomerRepository customerRepository;
    private final CustomerSiteRepository customerSiteRepository;
    private final SiteContactRepository siteContactRepository;
    private final CityRepository cityRepository;
    private final TechnicianRepository technicianRepository;
    private final DealerRepository dealerRepository;
    private final RepRepository repRepository;
    private final SalesmanRepository salesmanRepository;
    private final UserRepository userRepository;

    public MachineService(
            MachineRepository machineRepository,
            MachineModelRepository machineModelRepository,
            MachineInvoiceRepository machineInvoiceRepository,
            CustomerRepository customerRepository,
            CustomerSiteRepository customerSiteRepository,
            SiteContactRepository siteContactRepository,
            CityRepository cityRepository,
            TechnicianRepository technicianRepository,
            DealerRepository dealerRepository,
            RepRepository repRepository,
            SalesmanRepository salesmanRepository,
            UserRepository userRepository) {
        this.machineRepository = machineRepository;
        this.machineModelRepository = machineModelRepository;
        this.machineInvoiceRepository = machineInvoiceRepository;
        this.customerRepository = customerRepository;
        this.customerSiteRepository = customerSiteRepository;
        this.siteContactRepository = siteContactRepository;
        this.cityRepository = cityRepository;
        this.technicianRepository = technicianRepository;
        this.dealerRepository = dealerRepository;
        this.repRepository = repRepository;
        this.salesmanRepository = salesmanRepository;
        this.userRepository = userRepository;
    }

    public MachineResponse create(MachineRequest request) {
        MachineModel model = validateReferences(request);
        validateCreateUniqueValues(request);
        LocalDateTime now = LocalDateTime.now();

        try {
            CustomerSite site = createSite(request.customerSite(), request.performedBy(), now);
            SiteContact contact = createContact(
                    request.siteContact(), site.getCustomerSiteId(), request.performedBy(), now);
            MachineInvoice invoice = createInvoice(
                    request.machineInvoice(), request.customerSite().customerId(), request.performedBy(), now);
            Machine machine = createMachine(
                    request.machine(), site.getCustomerSiteId(), invoice.getMachineInvoiceId(),
                    request.performedBy(), now);

            return response(machine, model, site, contact, invoice);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Machine could not be created because a unique or referenced value is invalid",
                    exception);
        }
    }

    @Transactional(readOnly = true)
    public List<MachineResponse> getAll() {
        return machineRepository.findAllWithDetails()
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public MachineResponse getById(Long machineId) {
        return response(findMachine(machineId));
    }

    @Transactional(readOnly = true)
    public List<MachineResponse> search(String query) {
        if (query == null || query.isBlank()) {
            return getAll();
        }

        return machineRepository.search(query.trim())
                .stream()
                .map(this::response)
                .toList();
    }

    public MachineResponse update(Long machineId, MachineRequest request) {
        Machine machine = findMachine(machineId);
        CustomerSite site = findSite(machine.getCurrentCustomerSiteId());
        MachineInvoice invoice = findInvoice(machine.getMachineInvoiceId());
        SiteContact contact = findContact(site.getCustomerSiteId());
        MachineModel model = validateReferences(request);
        validateUpdateUniqueValues(machineId, site, contact, invoice, request);
        LocalDateTime now = LocalDateTime.now();

        try {
            updateSite(site, request.customerSite(), request.performedBy(), now);
            updateContact(contact, request.siteContact(), request.performedBy(), now);
            updateInvoice(invoice, request.machineInvoice(), request.customerSite().customerId());
            updateMachine(machine, request.machine(), request.performedBy(), now);

            customerSiteRepository.saveAndFlush(site);
            siteContactRepository.saveAndFlush(contact);
            machineInvoiceRepository.saveAndFlush(invoice);
            machineRepository.saveAndFlush(machine);

            return response(machine, model, site, contact, invoice);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Machine could not be updated because a unique or referenced value is invalid",
                    exception);
        }
    }

    public void delete(Long machineId) {
        Machine machine = findMachine(machineId);
        Long siteId = machine.getCurrentCustomerSiteId();
        Long invoiceId = machine.getMachineInvoiceId();

        try {
            machineRepository.delete(machine);
            machineRepository.flush();

            if (siteId != null) {
                siteContactRepository.deleteAll(siteContactRepository.findAllByCustomerSiteId(siteId));
                siteContactRepository.flush();
                customerSiteRepository.deleteById(siteId);
                customerSiteRepository.flush();
            }

            if (invoiceId != null) {
                machineInvoiceRepository.deleteById(invoiceId);
                machineInvoiceRepository.flush();
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Machine " + machineId + " cannot be deleted because it is referenced by other records",
                    exception);
        }
    }

    private MachineModel validateReferences(MachineRequest request) {
        CustomerSiteData site = request.customerSite();
        MachineData machine = request.machine();

        requireExists(customerRepository.existsById(site.customerId()), "Customer", site.customerId());
        requireExists(cityRepository.existsById(site.cityId()), "City", site.cityId());
        requireExists(userRepository.existsById(request.performedBy()), "User", request.performedBy());

        MachineModel model = machineModelRepository.findById(machine.modelId())
                .orElseThrow(() -> notFound("Machine model", machine.modelId()));

        if (model.getCompany() != machine.company()) {
            throw new IllegalArgumentException(
                    "Machine company must match the selected machine model company");
        }

        validateOptional(machine.currentMainTechnicianId(), technicianRepository::existsById, "Main technician");
        validateOptional(machine.currentServiceTechnicianId(), technicianRepository::existsById, "Service technician");
        validateOptional(machine.dealerId(), dealerRepository::existsById, "Dealer");
        validateOptional(machine.repId(), repRepository::existsById, "Rep");
        validateOptional(machine.salesmanId(), salesmanRepository::existsById, "Salesman");

        return model;
    }

    private void validateCreateUniqueValues(MachineRequest request) {
        String serial = normalizeCode(request.machine().serialNumber());
        String invoice = normalizeCode(request.machineInvoice().invoiceNumber());
        String siteName = request.customerSite().siteName().trim();

        if (machineRepository.existsBySerialNumberIgnoreCase(serial)) {
            throw duplicate("Machine serial number", serial);
        }
        if (machineInvoiceRepository.existsByInvoiceNumberIgnoreCase(invoice)) {
            throw duplicate("Invoice number", invoice);
        }
        if (customerSiteRepository.existsByCustomerIdAndSiteNameIgnoreCase(
                request.customerSite().customerId(), siteName)) {
            throw duplicate("Customer site", siteName);
        }
    }

    private void validateUpdateUniqueValues(
            Long machineId,
            CustomerSite site,
            SiteContact contact,
            MachineInvoice invoice,
            MachineRequest request) {
        String serial = normalizeCode(request.machine().serialNumber());
        String invoiceNumber = normalizeCode(request.machineInvoice().invoiceNumber());
        String siteName = request.customerSite().siteName().trim();
        String email = request.siteContact().email().trim();

        if (machineRepository.existsBySerialNumberIgnoreCaseAndMachineIdNot(serial, machineId)) {
            throw duplicate("Machine serial number", serial);
        }
        if (machineInvoiceRepository.existsByInvoiceNumberIgnoreCaseAndMachineInvoiceIdNot(
                invoiceNumber, invoice.getMachineInvoiceId())) {
            throw duplicate("Invoice number", invoiceNumber);
        }
        if (customerSiteRepository.existsByCustomerIdAndSiteNameIgnoreCaseAndCustomerSiteIdNot(
                request.customerSite().customerId(), siteName, site.getCustomerSiteId())) {
            throw duplicate("Customer site", siteName);
        }
        if (siteContactRepository.existsByCustomerSiteIdAndEmailIgnoreCaseAndSiteContactIdNot(
                site.getCustomerSiteId(), email, contact.getSiteContactId())) {
            throw duplicate("Site contact email", email);
        }
    }

    private CustomerSite createSite(CustomerSiteData data, Long performedBy, LocalDateTime now) {
        CustomerSite site = new CustomerSite();
        applySite(site, data);
        site.setCreatedBy(performedBy);
        site.setCreatedAt(now);
        return customerSiteRepository.saveAndFlush(site);
    }

    private SiteContact createContact(
            SiteContactData data,
            Long siteId,
            Long performedBy,
            LocalDateTime now) {
        SiteContact contact = new SiteContact();
        contact.setCustomerSiteId(siteId);
        applyContact(contact, data);
        contact.setCreatedBy(performedBy);
        contact.setCreatedAt(now);
        return siteContactRepository.saveAndFlush(contact);
    }

    private MachineInvoice createInvoice(
            MachineInvoiceData data,
            Long customerId,
            Long performedBy,
            LocalDateTime now) {
        MachineInvoice invoice = new MachineInvoice();
        applyInvoice(invoice, data, customerId);
        invoice.setCreatedBy(performedBy);
        invoice.setCreatedAt(now);
        return machineInvoiceRepository.saveAndFlush(invoice);
    }

    private Machine createMachine(
            MachineData data,
            Long siteId,
            Long invoiceId,
            Long performedBy,
            LocalDateTime now) {
        Machine machine = new Machine();
        machine.setMachineReferenceNumber(nextReferenceNumber());
        machine.setCurrentCustomerSiteId(siteId);
        machine.setMachineInvoiceId(invoiceId);
        applyMachine(machine, data);
        machine.setCreatedBy(performedBy);
        machine.setCreatedAt(now);
        return machineRepository.saveAndFlush(machine);
    }

    private void updateSite(
            CustomerSite site,
            CustomerSiteData data,
            Long performedBy,
            LocalDateTime now) {
        applySite(site, data);
        site.setUpdatedBy(performedBy);
        site.setUpdatedAt(now);
    }

    private void updateContact(
            SiteContact contact,
            SiteContactData data,
            Long performedBy,
            LocalDateTime now) {
        applyContact(contact, data);
        contact.setUpdatedBy(performedBy);
        contact.setUpdatedAt(now);
    }

    private void updateInvoice(MachineInvoice invoice, MachineInvoiceData data, Long customerId) {
        applyInvoice(invoice, data, customerId);
    }

    private void updateMachine(
            Machine machine,
            MachineData data,
            Long performedBy,
            LocalDateTime now) {
        applyMachine(machine, data);
        machine.setUpdatedBy(performedBy);
        machine.setUpdatedAt(now);
    }

    private void applySite(CustomerSite site, CustomerSiteData data) {
        site.setCustomerId(data.customerId());
        site.setSiteName(data.siteName().trim());
        site.setAddressLine1(data.addressLine1().trim());
        site.setAddressLine2(optional(data.addressLine2()));
        site.setAddressLine3(optional(data.addressLine3()));
        site.setArea(data.area());
        site.setCityId(data.cityId());
        site.setLatitude(data.latitude());
        site.setLongitude(data.longitude());
        site.setIsHeadOffice(Boolean.TRUE.equals(data.isHeadOffice()));
        site.setIsActive(data.isActive() == null || data.isActive());
    }

    private void applyContact(SiteContact contact, SiteContactData data) {
        contact.setContactName(data.contactName().trim());
        contact.setMobileNumber(optional(data.mobileNumber()));
        contact.setEmail(data.email().trim().toLowerCase(Locale.ROOT));
        contact.setDesignation(optional(data.designation()));
        contact.setIsPrimary(Boolean.TRUE.equals(data.isPrimary()));
        contact.setIsActive(data.isActive() == null || data.isActive());
    }

    private void applyInvoice(MachineInvoice invoice, MachineInvoiceData data, Long customerId) {
        invoice.setInvoiceNumber(normalizeCode(data.invoiceNumber()));
        invoice.setBelitaInvoiceNumber(optional(data.belitaInvoiceNumber()));
        invoice.setInvoiceDate(data.invoiceDate());
        invoice.setCustomerId(customerId);
        invoice.setNote(optional(data.note()));
    }

    private void applyMachine(Machine machine, MachineData data) {
        machine.setSerialNumber(normalizeCode(data.serialNumber()));
        machine.setCompany(data.company());
        machine.setDivision(data.division());
        machine.setModelId(data.modelId());
        machine.setCurrentStatus(data.currentStatus());
        machine.setCurrentMainTechnicianId(data.currentMainTechnicianId());
        machine.setCurrentServiceTechnicianId(data.currentServiceTechnicianId());
        machine.setDealerId(data.dealerId());
        machine.setRepId(data.repId());
        machine.setSalesmanId(data.salesmanId());
        machine.setOriginalInstallDate(data.originalInstallDate());
        machine.setNote(optional(data.note()));
        machine.setCreditNoteNumber(optional(data.creditNoteNumber()));
    }

    private MachineResponse response(Machine machine) {
        CustomerSite site = machine.getCurrentCustomerSite();
        MachineInvoice invoice = machine.getMachineInvoice();
        SiteContact contact = findContact(site.getCustomerSiteId());
        return response(machine, machine.getModel(), site, contact, invoice);
    }

    private MachineResponse response(
            Machine machine,
            MachineModel model,
            CustomerSite site,
            SiteContact contact,
            MachineInvoice invoice) {
        return new MachineResponse(
                machine.getMachineId(),
                machine.getMachineReferenceNumber(),
                machine.getSerialNumber(),
                machine.getCompany(),
                machine.getDivision(),
                machine.getModelId(),
                model.getModelNumber(),
                model.getModelName(),
                machine.getCurrentStatus(),
                machine.getCurrentMainTechnicianId(),
                machine.getCurrentServiceTechnicianId(),
                machine.getDealerId(),
                machine.getRepId(),
                machine.getSalesmanId(),
                machine.getOriginalInstallDate(),
                machine.getNote(),
                machine.getCreditNoteNumber(),
                site.getCustomerSiteId(),
                site.getCustomerId(),
                site.getSiteName(),
                site.getAddressLine1(),
                site.getAddressLine2(),
                site.getAddressLine3(),
                site.getArea(),
                site.getCityId(),
                site.getLatitude(),
                site.getLongitude(),
                site.getIsHeadOffice(),
                site.getIsActive(),
                contact.getSiteContactId(),
                contact.getContactName(),
                contact.getMobileNumber(),
                contact.getEmail(),
                contact.getDesignation(),
                contact.isPrimary(),
                contact.isActive(),
                invoice.getMachineInvoiceId(),
                invoice.getInvoiceNumber(),
                invoice.getBelitaInvoiceNumber(),
                invoice.getInvoiceDate(),
                invoice.getNote(),
                machine.getCreatedBy(),
                machine.getCreatedAt(),
                machine.getUpdatedBy(),
                machine.getUpdatedAt());
    }

    private Machine findMachine(Long machineId) {
        return machineRepository.findByIdWithDetails(machineId)
                .orElseThrow(() -> notFound("Machine", machineId));
    }

    private CustomerSite findSite(Long siteId) {
        if (siteId == null) {
            throw new EntityNotFoundException("Machine does not have a customer site");
        }
        return customerSiteRepository.findById(siteId)
                .orElseThrow(() -> notFound("Customer site", siteId));
    }

    private SiteContact findContact(Long siteId) {
        return siteContactRepository.findFirstByCustomerSiteIdOrderByIsPrimaryDescSiteContactIdAsc(siteId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "A site contact was not found for customer site " + siteId));
    }

    private MachineInvoice findInvoice(Long invoiceId) {
        if (invoiceId == null) {
            throw new EntityNotFoundException("Machine does not have an invoice");
        }
        return machineInvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> notFound("Machine invoice", invoiceId));
    }

    private String nextReferenceNumber() {
        return "Q" + String.format("%06d", machineRepository.findMaximumReferenceSequence() + 1);
    }

    private void validateOptional(
            Long id,
            java.util.function.Predicate<Long> exists,
            String resourceName) {
        if (id != null) {
            requireExists(exists.test(id), resourceName, id);
        }
    }

    private void requireExists(boolean exists, String resourceName, Long id) {
        if (!exists) {
            throw notFound(resourceName, id);
        }
    }

    private EntityNotFoundException notFound(String resourceName, Long id) {
        return new EntityNotFoundException(resourceName + " " + id + " was not found");
    }

    private IllegalStateException duplicate(String field, String value) {
        return new IllegalStateException(field + " " + value + " already exists");
    }

    private String normalizeCode(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
