package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.machines.MachineInvoiceRequest;
import com.gestetner.servvista.Dto.machines.MachineInvoiceResponse;
import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import com.gestetner.servvista.Repositories.customers.CustomerRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineInvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class MachineInvoiceService {

    private final MachineInvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public MachineInvoiceService(
            MachineInvoiceRepository invoiceRepository,
            CustomerRepository customerRepository,
            UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public MachineInvoiceResponse create(MachineInvoiceRequest request) {
        validateReferences(request);
        String invoiceNumber = normalizeCode(request.invoiceNumber());
        ensureUniqueInvoiceNumber(invoiceNumber, null);

        try {
            MachineInvoice invoice = new MachineInvoice();
            apply(invoice, request, invoiceNumber);
            invoice.setCreatedBy(request.createdBy());
            invoice.setCreatedAt(LocalDateTime.now());
            invoice = invoiceRepository.saveAndFlush(invoice);
            Long savedInvoiceId = invoice.getMachineInvoiceId();
            return response(invoiceRepository.findByIdWithCustomer(savedInvoiceId)
                    .orElseThrow(() -> notFound(savedInvoiceId)));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Machine invoice could not be created", exception);
        }
    }

    @Transactional(readOnly = true)
    public List<MachineInvoiceResponse> getAll() {
        return invoiceRepository.findAllWithCustomer().stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public MachineInvoiceResponse getById(Long machineInvoiceId) {
        return response(find(machineInvoiceId));
    }

    public MachineInvoiceResponse update(
            Long machineInvoiceId, MachineInvoiceRequest request) {
        MachineInvoice invoice = find(machineInvoiceId);
        validateCustomer(request.customerId());
        String invoiceNumber = normalizeCode(request.invoiceNumber());
        ensureUniqueInvoiceNumber(invoiceNumber, machineInvoiceId);

        try {
            apply(invoice, request, invoiceNumber);
            invoiceRepository.saveAndFlush(invoice);
            return response(invoiceRepository.findByIdWithCustomer(machineInvoiceId)
                    .orElseThrow(() -> notFound(machineInvoiceId)));
        } catch (DataIntegrityViolationException exception) {
            throw conflict("Machine invoice could not be updated", exception);
        }
    }

    public void delete(Long machineInvoiceId) {
        MachineInvoice invoice = find(machineInvoiceId);
        try {
            invoiceRepository.delete(invoice);
            invoiceRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Machine invoice " + machineInvoiceId
                            + " cannot be deleted because it is referenced by another record",
                    exception);
        }
    }

    private void validateReferences(MachineInvoiceRequest request) {
        validateCustomer(request.customerId());
        if (request.createdBy() != null && !userRepository.existsById(request.createdBy())) {
            throw new EntityNotFoundException(
                    "User " + request.createdBy() + " was not found");
        }
    }

    private void validateCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer " + customerId + " was not found");
        }
    }

    private void ensureUniqueInvoiceNumber(String invoiceNumber, Long machineInvoiceId) {
        boolean exists = machineInvoiceId == null
                ? invoiceRepository.existsByInvoiceNumberIgnoreCase(invoiceNumber)
                : invoiceRepository.existsByInvoiceNumberIgnoreCaseAndMachineInvoiceIdNot(
                        invoiceNumber, machineInvoiceId);
        if (exists) {
            throw new IllegalStateException(
                    "Machine invoice number '" + invoiceNumber + "' already exists");
        }
    }

    private void apply(
            MachineInvoice invoice, MachineInvoiceRequest request, String invoiceNumber) {
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setBelitaInvoiceNumber(normalizeOptionalCode(request.belitaInvoiceNumber()));
        invoice.setInvoiceDate(request.invoiceDate());
        invoice.setCustomerId(request.customerId());
        invoice.setNote(optional(request.note()));
    }

    private MachineInvoice find(Long machineInvoiceId) {
        return invoiceRepository.findByIdWithCustomer(machineInvoiceId)
                .orElseThrow(() -> notFound(machineInvoiceId));
    }

    private EntityNotFoundException notFound(Long machineInvoiceId) {
        return new EntityNotFoundException(
                "Machine invoice " + machineInvoiceId + " was not found");
    }

    private MachineInvoiceResponse response(MachineInvoice invoice) {
        String customerName = invoice.getCustomer() != null
                ? invoice.getCustomer().getCustomerName()
                : customerRepository.findById(invoice.getCustomerId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Customer " + invoice.getCustomerId() + " was not found"))
                        .getCustomerName();
        return new MachineInvoiceResponse(
                invoice.getMachineInvoiceId(), invoice.getInvoiceNumber(),
                invoice.getBelitaInvoiceNumber(), invoice.getInvoiceDate(),
                invoice.getCustomerId(), customerName,
                invoice.getNote(), invoice.getCreatedBy(), invoice.getCreatedAt());
    }

    private String normalizeCode(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeOptionalCode(String value) {
        return value == null || value.isBlank()
                ? null
                : value.trim().toUpperCase(Locale.ROOT);
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private IllegalStateException conflict(String message, Exception exception) {
        return new IllegalStateException(
                message + " because a unique or referenced value is invalid", exception);
    }
}
