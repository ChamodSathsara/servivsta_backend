package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.catalog.MachineModelRequest;
import com.gestetner.servvista.Dto.catalog.MachineModelResponse;
import com.gestetner.servvista.Models.entity.machines.MachineModel;
import com.gestetner.servvista.Models.entity.machines.Manufacturer;
import com.gestetner.servvista.Models.entity.machines.MachineType;
import com.gestetner.servvista.Repositories.machines.MachineModelRepository;
import com.gestetner.servvista.Repositories.machines.MachineTypeRepository;
import com.gestetner.servvista.Repositories.machines.ManufacturerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service @Transactional
public class MachineModelService {
    private final MachineModelRepository repository;
    private final ManufacturerRepository manufacturerRepository;
    private final MachineTypeRepository machineTypeRepository;
    public MachineModelService(MachineModelRepository repository, ManufacturerRepository manufacturerRepository,
                               MachineTypeRepository machineTypeRepository) {
        this.repository = repository; this.manufacturerRepository = manufacturerRepository;
        this.machineTypeRepository = machineTypeRepository;
    }
    public MachineModelResponse create(MachineModelRequest request) {
        ReferenceData references = validateReferences(request.manufacturerId(), request.machineTypeId());
        String number = code(request.modelNumber());
        try {
            MachineModel model = new MachineModel(); apply(model, request, number, true);
            model = repository.saveAndFlush(model);
            return response(model, references.manufacturer(), references.machineType());
        } catch (DataIntegrityViolationException e) { throw conflict(e); }
    }
    @Transactional(readOnly = true)
    public List<MachineModelResponse> getAll() { return repository.findAllWithReferences().stream().map(this::response).toList(); }
    public MachineModelResponse update(Long id, MachineModelRequest request) {
        MachineModel model = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Machine model " + id + " was not found"));
        ReferenceData references = validateReferences(request.manufacturerId(), request.machineTypeId());
        String number = code(request.modelNumber());
        try { apply(model, request, number, false); repository.saveAndFlush(model);
            return response(model, references.manufacturer(), references.machineType()); }
        catch (DataIntegrityViolationException e) { throw conflict(e); }
    }
    public void delete(Long id) {
        MachineModel model = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Machine model " + id + " was not found"));
        try { repository.delete(model); repository.flush(); }
        catch (DataIntegrityViolationException e) { throw new IllegalStateException(
                "Machine model " + id + " cannot be deleted because it is referenced by other records", e); }
    }
    private ReferenceData validateReferences(Long manufacturerId, Long machineTypeId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Manufacturer " + manufacturerId + " was not found"));
        MachineType machineType = machineTypeRepository.findById(machineTypeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Machine type " + machineTypeId + " was not found"));
        return new ReferenceData(manufacturer, machineType);
    }
    private void apply(MachineModel m, MachineModelRequest r, String number, boolean create) {
        m.setCompany(r.company()); m.setManufacturerId(r.manufacturerId()); m.setMachineTypeId(r.machineTypeId());
        m.setModelNumber(number); m.setModelName(r.modelName().trim()); m.setDescription(optional(r.description()));
        if (create) { m.setIsActive(r.isActive() == null || r.isActive()); m.setCreatedAt(LocalDateTime.now()); m.setCreatedBy(r.createdBy()); }
        else if (r.isActive() != null) m.setIsActive(r.isActive());
    }
    private MachineModel findWithReferences(Long id) { return repository.findByIdWithReferences(id).orElseThrow(() ->
            new EntityNotFoundException("Machine model " + id + " was not found")); }
    private MachineModelResponse response(MachineModel m) { return new MachineModelResponse(m.getModelId(), m.getCompany(),
            m.getManufacturerId(), m.getManufacturer().getManufacturerName(), m.getMachineTypeId(),
            m.getMachineType().getMachineTypeName(), m.getModelNumber(), m.getModelName(), m.getDescription(),
            m.getIsActive(), m.getCreatedAt(), m.getCreatedBy()); }
    private MachineModelResponse response(MachineModel m, Manufacturer manufacturer, MachineType machineType) {
        return new MachineModelResponse(m.getModelId(), m.getCompany(), m.getManufacturerId(),
                manufacturer.getManufacturerName(), m.getMachineTypeId(), machineType.getMachineTypeName(),
                m.getModelNumber(), m.getModelName(), m.getDescription(), m.getIsActive(),
                m.getCreatedAt(), m.getCreatedBy());
    }
    private String code(String value) { return value.trim().toUpperCase(Locale.ROOT); }
    private String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private IllegalStateException conflict(Exception e) { return new IllegalStateException(
            "Machine model could not be saved because a unique or referenced value is invalid", e); }
    private record ReferenceData(Manufacturer manufacturer, MachineType machineType) {}
}
