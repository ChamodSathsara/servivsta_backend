package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.catalog.MachineTypeRequest;
import com.gestetner.servvista.Dto.catalog.MachineTypeResponse;
import com.gestetner.servvista.Models.entity.machines.MachineType;
import com.gestetner.servvista.Repositories.machines.MachineTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Transactional
public class MachineTypeService {
    private final MachineTypeRepository repository;
    public MachineTypeService(MachineTypeRepository repository) { this.repository = repository; }
    public MachineTypeResponse create(MachineTypeRequest request) {
        String name = request.machineTypeName().trim(); ensureName(name, null);
        try { MachineType type = new MachineType(); apply(type, request, name, true); return response(repository.saveAndFlush(type)); }
        catch (DataIntegrityViolationException e) { throw duplicate(e); }
    }
    @Transactional(readOnly = true)
    public List<MachineTypeResponse> getAll() { return repository.findAll(Sort.by("machineTypeId")).stream().map(this::response).toList(); }
    public MachineTypeResponse update(Long id, MachineTypeRequest request) {
        MachineType type = find(id); String name = request.machineTypeName().trim(); ensureName(name, id);
        try { apply(type, request, name, false); return response(repository.saveAndFlush(type)); }
        catch (DataIntegrityViolationException e) { throw duplicate(e); }
    }
    public void delete(Long id) {
        MachineType type = find(id);
        try { repository.delete(type); repository.flush(); }
        catch (DataIntegrityViolationException e) { throw new IllegalStateException(
                "Machine type " + id + " cannot be deleted because it is referenced by a machine model", e); }
    }
    private MachineType find(Long id) { return repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Machine type " + id + " was not found")); }
    private void ensureName(String name, Long id) {
        boolean exists = id == null ? repository.existsByMachineTypeNameIgnoreCase(name)
                : repository.existsByMachineTypeNameIgnoreCaseAndMachineTypeIdNot(name, id);
        if (exists) throw new IllegalStateException("Machine type '" + name + "' already exists");
    }
    private void apply(MachineType t, MachineTypeRequest r, String name, boolean create) {
        t.setMachineTypeName(name); t.setMachineTypeDescription(optional(r.machineTypeDescription()));
        if (create) t.setIsActive(r.isActive() == null || r.isActive()); else if (r.isActive() != null) t.setIsActive(r.isActive());
    }
    private MachineTypeResponse response(MachineType t) { return new MachineTypeResponse(t.getMachineTypeId(),
            t.getMachineTypeName(), t.getMachineTypeDescription(), t.isActive()); }
    private String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private IllegalStateException duplicate(Exception e) { return new IllegalStateException("Machine type name already exists", e); }
}
