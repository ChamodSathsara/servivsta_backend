package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.agreements.MachineAgreementRequest;
import com.gestetner.servvista.Dto.agreements.MachineAgreementResponse;
import com.gestetner.servvista.Dto.installations.InstallationJobRequest;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
import com.gestetner.servvista.Dto.sales.NewSaleRequest;
import com.gestetner.servvista.Dto.sales.NewSaleResponse;
import com.gestetner.servvista.Models.Enums.Machines.TechnicianAssignmentRole;
import com.gestetner.servvista.Models.Enums.Machines.WarrantyStatus;
import com.gestetner.servvista.Models.entity.customers.CustomerSite;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.machines.MachineAssignment;
import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import com.gestetner.servvista.Models.entity.machines.MachineLiveLocation;
import com.gestetner.servvista.Models.entity.machines.MachineModel;
import com.gestetner.servvista.Models.entity.machines.MachineStatusHistory;
import com.gestetner.servvista.Models.entity.machines.MachineTechnicianAssignment;
import com.gestetner.servvista.Models.entity.machines.MachineWarranty;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import com.gestetner.servvista.Repositories.customers.CustomerSiteRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineAssignmentRepository;
import com.gestetner.servvista.Repositories.machines.MachineInvoiceRepository;
import com.gestetner.servvista.Repositories.machines.MachineLiveLocationRepository;
import com.gestetner.servvista.Repositories.machines.MachineModelRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.machines.MachineStatusHistoryRepository;
import com.gestetner.servvista.Repositories.machines.MachineTechnicianAssignmentRepository;
import com.gestetner.servvista.Repositories.machines.MachineWarrantyRepository;
import com.gestetner.servvista.Repositories.sales.DealerRepository;
import com.gestetner.servvista.Repositories.sales.RepRepository;
import com.gestetner.servvista.Repositories.sales.SalesmanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;
import java.util.function.Predicate;

@Service
@Transactional
public class NewSaleService {

    private final MachineRepository machineRepository;
    private final MachineModelRepository modelRepository;
    private final MachineInvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final CustomerSiteRepository siteRepository;
    private final TechnicianRepository technicianRepository;
    private final DealerRepository dealerRepository;
    private final RepRepository repRepository;
    private final SalesmanRepository salesmanRepository;
    private final UserRepository userRepository;
    private final MachineStatusHistoryRepository machineStatusHistoryRepository;
    private final MachineAssignmentRepository machineAssignmentRepository;
    private final MachineTechnicianAssignmentRepository technicianAssignmentRepository;
    private final MachineWarrantyRepository warrantyRepository;
    private final MachineLiveLocationRepository liveLocationRepository;
    private final InstallationService installationService;
    private final MachineAgreementService agreementService;

    public NewSaleService(
            MachineRepository machineRepository,
            MachineModelRepository modelRepository,
            MachineInvoiceRepository invoiceRepository,
            CustomerRepository customerRepository,
            CustomerSiteRepository siteRepository,
            TechnicianRepository technicianRepository,
            DealerRepository dealerRepository,
            RepRepository repRepository,
            SalesmanRepository salesmanRepository,
            UserRepository userRepository,
            MachineStatusHistoryRepository machineStatusHistoryRepository,
            MachineAssignmentRepository machineAssignmentRepository,
            MachineTechnicianAssignmentRepository technicianAssignmentRepository,
            MachineWarrantyRepository warrantyRepository,
            MachineLiveLocationRepository liveLocationRepository,
            InstallationService installationService,
            MachineAgreementService agreementService) {
        this.machineRepository = machineRepository;
        this.modelRepository = modelRepository;
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.siteRepository = siteRepository;
        this.technicianRepository = technicianRepository;
        this.dealerRepository = dealerRepository;
        this.repRepository = repRepository;
        this.salesmanRepository = salesmanRepository;
        this.userRepository = userRepository;
        this.machineStatusHistoryRepository = machineStatusHistoryRepository;
        this.machineAssignmentRepository = machineAssignmentRepository;
        this.technicianAssignmentRepository = technicianAssignmentRepository;
        this.warrantyRepository = warrantyRepository;
        this.liveLocationRepository = liveLocationRepository;
        this.installationService = installationService;
        this.agreementService = agreementService;
    }

    public NewSaleResponse create(NewSaleRequest request) {
        validate(request);
        LocalDateTime now = LocalDateTime.now();

        try {
            Machine machine = createMachine(request, now);
            MachineStatusHistory machineHistory = createMachineStatusHistory(machine, request, now);

            InstallationJobResponse installation = installationService.createJob(
                    installationRequest(request));
            MachineAgreementResponse agreement = agreementService.create(
                    agreementRequest(request, machine.getMachineId(), installation.installationJobId()));

            MachineAssignment assignment = createMachineAssignment(
                    request, machine.getMachineId(), agreement.agreementId(),
                    installation.installationJobId(), now);
            MachineTechnicianAssignment mainAssignment = createTechnicianAssignment(
                    request, machine.getMachineId(), request.technicians().mainTechnicianId(),
                    TechnicianAssignmentRole.MAIN, now);
            MachineTechnicianAssignment serviceAssignment = createTechnicianAssignment(
                    request, machine.getMachineId(), request.technicians().serviceTechnicianId(),
                    TechnicianAssignmentRole.SERVICE, now);
            MachineWarranty warranty = createWarranty(request, machine.getMachineId(), now);
            MachineLiveLocation location = createLocation(request, machine.getMachineId(), now);

            return new NewSaleResponse(
                    machine.getMachineId(), machine.getMachineReferenceNumber(), machine.getSerialNumber(),
                    machine.getCurrentStatus(), machineHistory.getMachineStatusHistoryId(),
                    assignment.getMachineAssignmentId(),
                    mainAssignment.getMachineTechnicianAssignmentId(),
                    serviceAssignment.getMachineTechnicianAssignmentId(),
                    warranty.getMachineWarrantyId(), warranty.getStatus(), location.getMachineId(),
                    installation.installationJobId(), installation.jobNumber(), installation.status(),
                    agreement.agreementId(), agreement.agreementNumber(), agreement.agreementStatus(), now);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "New sale could not be created because a unique or referenced value is invalid",
                    exception);
        }
    }

    private void validate(NewSaleRequest request) {
        NewSaleRequest.MachineData machine = request.machine();
        NewSaleRequest.AssignmentData assignment = request.assignment();

        require(userRepository.existsById(request.performedBy()), "User", request.performedBy());
        require(customerRepository.existsById(assignment.customerId()), "Customer", assignment.customerId());
        require(technicianRepository.existsById(request.technicians().mainTechnicianId()),
                "Main technician", request.technicians().mainTechnicianId());
        require(technicianRepository.existsById(request.technicians().serviceTechnicianId()),
                "Service technician", request.technicians().serviceTechnicianId());

        MachineModel model = modelRepository.findById(machine.modelId())
                .orElseThrow(() -> notFound("Machine model", machine.modelId()));
        if (model.getCompany() != machine.company()) {
            throw new IllegalArgumentException(
                    "Machine company must match the selected machine model company");
        }

        CustomerSite site = siteRepository.findById(machine.customerSiteId())
                .orElseThrow(() -> notFound("Customer site", machine.customerSiteId()));
        if (!site.getCustomerId().equals(assignment.customerId())) {
            throw new IllegalArgumentException("Customer site does not belong to the selected customer");
        }

        MachineInvoice invoice = invoiceRepository.findById(machine.machineInvoiceId())
                .orElseThrow(() -> notFound("Machine invoice", machine.machineInvoiceId()));
        if (!invoice.getCustomerId().equals(assignment.customerId())) {
            throw new IllegalArgumentException("Machine invoice does not belong to the selected customer");
        }

        validateOptional(machine.dealerId(), dealerRepository::existsById, "Dealer");
        validateOptional(machine.repId(), repRepository::existsById, "Rep");
        validateOptional(machine.salesmanId(), salesmanRepository::existsById, "Salesman");

        String serialNumber = machine.serialNumber().trim().toUpperCase(Locale.ROOT);
        if (machineRepository.existsBySerialNumberIgnoreCase(serialNumber)) {
            throw new IllegalStateException("Machine serial number '" + serialNumber + "' already exists");
        }
        if (request.warranty().endDate().isBefore(request.warranty().startDate())) {
            throw new IllegalArgumentException("Warranty end date cannot be before its start date");
        }
    }

    private Machine createMachine(NewSaleRequest request, LocalDateTime now) {
        NewSaleRequest.MachineData data = request.machine();
        Machine machine = new Machine();
        machine.setMachineReferenceNumber(
                "Q" + String.format("%06d", machineRepository.findMaximumReferenceSequence() + 1));
        machine.setSerialNumber(data.serialNumber().trim().toUpperCase(Locale.ROOT));
        machine.setCompany(data.company());
        machine.setDivision(data.division());
        machine.setModelId(data.modelId());
        machine.setCurrentStatus(data.currentStatus());
        machine.setCurrentCustomerSiteId(data.customerSiteId());
        machine.setCurrentMainTechnicianId(request.technicians().mainTechnicianId());
        machine.setCurrentServiceTechnicianId(request.technicians().serviceTechnicianId());
        machine.setMachineInvoiceId(data.machineInvoiceId());
        machine.setDealerId(data.dealerId());
        machine.setRepId(data.repId());
        machine.setSalesmanId(data.salesmanId());
        machine.setOriginalInstallDate(data.originalInstallDate());
        machine.setNote(optional(data.note()));
        machine.setCreditNoteNumber(optional(data.creditNoteNumber()));
        machine.setCreatedBy(request.performedBy());
        machine.setCreatedAt(now);
        return machineRepository.saveAndFlush(machine);
    }

    private MachineStatusHistory createMachineStatusHistory(
            Machine machine, NewSaleRequest request, LocalDateTime now) {
        MachineStatusHistory history = new MachineStatusHistory();
        history.setMachineId(machine.getMachineId());
        history.setPreviousStatus(machine.getCurrentStatus());
        history.setNewStatus(machine.getCurrentStatus());
        history.setChangedAt(now);
        history.setChangedBy(request.performedBy());
        history.setReason(optional(request.machineStatusReason()));
        return machineStatusHistoryRepository.saveAndFlush(history);
    }

    private InstallationJobRequest installationRequest(NewSaleRequest request) {
        return new InstallationJobRequest(
                request.machine().company(), request.machine().division(),
                request.assignment().customerId(), request.machine().customerSiteId(),
                request.machine().machineInvoiceId(), request.machine().dealerId(),
                request.machine().repId(), request.technicians().mainTechnicianId(),
                request.installation().expectedInstallDate(), request.installation().status(),
                request.performedBy(), request.installation().statusNote());
    }

    private MachineAgreementRequest agreementRequest(
            NewSaleRequest request, Long machineId, Long installationJobId) {
        NewSaleRequest.AgreementData data = request.agreement();
        return new MachineAgreementRequest(
                machineId, data.agreementType(), data.startDate(), data.endDate(),
                data.periodYears(), data.visitsPerYear(), data.annualPayment(),
                data.fullPayment(), data.discount(), data.vatPercentage(), data.vatAmount(),
                data.status(), data.isActive(), installationJobId, data.previousAgreementId(),
                data.note(), request.performedBy(), data.statusReason());
    }

    private MachineAssignment createMachineAssignment(
            NewSaleRequest request, Long machineId, Long agreementId,
            Long installationJobId, LocalDateTime now) {
        MachineAssignment assignment = new MachineAssignment();
        assignment.setMachineId(machineId);
        assignment.setCustomerId(request.assignment().customerId());
        assignment.setCustomerSiteId(request.machine().customerSiteId());
        assignment.setAssignmentType(request.assignment().assignmentType());
        assignment.setAgreementId(agreementId);
        assignment.setInstallationJobId(installationJobId);
        assignment.setAssignedFrom(request.assignment().assignedFrom());
        assignment.setIsCurrent(true);
        assignment.setCreatedBy(request.performedBy());
        assignment.setCreatedAt(now);
        return machineAssignmentRepository.saveAndFlush(assignment);
    }

    private MachineTechnicianAssignment createTechnicianAssignment(
            NewSaleRequest request, Long machineId, Long technicianId,
            TechnicianAssignmentRole role, LocalDateTime now) {
        MachineTechnicianAssignment assignment = new MachineTechnicianAssignment();
        assignment.setMachineId(machineId);
        assignment.setTechnicianId(technicianId);
        assignment.setAssignmentRole(role);
        assignment.setAssignedFrom(request.technicians().assignedFrom());
        assignment.setIsCurrent(true);
        assignment.setAssignedBy(request.performedBy());
        assignment.setCreatedAt(now);
        return technicianAssignmentRepository.saveAndFlush(assignment);
    }

    private MachineWarranty createWarranty(
            NewSaleRequest request, Long machineId, LocalDateTime now) {
        NewSaleRequest.WarrantyData data = request.warranty();
        MachineWarranty warranty = new MachineWarranty();
        warranty.setMachineId(machineId);
        warranty.setWarrantyType(data.warrantyType());
        warranty.setStartDate(data.startDate());
        warranty.setEndDate(data.endDate());
        long calculatedMonths = Period.between(data.startDate(), data.endDate()).toTotalMonths();
        warranty.setDurationMonths(data.durationMonths() == null
                ? (int) Math.max(1, calculatedMonths)
                : data.durationMonths());
        warranty.setStatus(data.status() == null ? WarrantyStatus.ACTIVE : data.status());
        warranty.setNote(optional(data.note()));
        warranty.setCreatedBy(request.performedBy());
        warranty.setCreatedAt(now);
        return warrantyRepository.saveAndFlush(warranty);
    }

    private MachineLiveLocation createLocation(
            NewSaleRequest request, Long machineId, LocalDateTime now) {
        MachineLiveLocation location = new MachineLiveLocation();
        location.setMachineId(machineId);
        location.setLatitude(request.liveLocation().latitude());
        location.setLongitude(request.liveLocation().longitude());
        location.setUpdatedAt(now);
        location.setUpdatedByUserId(request.performedBy());
        return liveLocationRepository.saveAndFlush(location);
    }

    private void validateOptional(Long id, Predicate<Long> exists, String name) {
        if (id != null) {
            require(exists.test(id), name, id);
        }
    }

    private void require(boolean exists, String name, Long id) {
        if (!exists) {
            throw notFound(name, id);
        }
    }

    private EntityNotFoundException notFound(String name, Long id) {
        return new EntityNotFoundException(name + " " + id + " was not found");
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

}
