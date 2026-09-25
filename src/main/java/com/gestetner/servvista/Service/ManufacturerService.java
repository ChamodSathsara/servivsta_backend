package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.catalog.ManufacturerRequest;
import com.gestetner.servvista.Dto.catalog.ManufacturerResponse;
import com.gestetner.servvista.Models.entity.machines.Manufacturer;
import com.gestetner.servvista.Repositories.machines.ManufacturerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service @Transactional
public class ManufacturerService {
    private final ManufacturerRepository repository;
    public ManufacturerService(ManufacturerRepository repository) { this.repository = repository; }
    public ManufacturerResponse create(ManufacturerRequest request) {
        String name = request.manufacturerName().trim(); ensureName(name, null);
        try { Manufacturer m = new Manufacturer(); apply(m, request, name, true); return response(repository.saveAndFlush(m)); }
        catch (DataIntegrityViolationException e) { throw duplicate(e); }
    }
    @Transactional(readOnly = true)
    public List<ManufacturerResponse> getAll() { return repository.findAll(Sort.by("manufacturerId")).stream().map(this::response).toList(); }
    public ManufacturerResponse update(Long id, ManufacturerRequest request) {
        Manufacturer m = find(id); String name = request.manufacturerName().trim(); ensureName(name, id);
        try { apply(m, request, name, false); return response(repository.saveAndFlush(m)); }
        catch (DataIntegrityViolationException e) { throw duplicate(e); }
    }
    public void delete(Long id) {
        Manufacturer m = find(id);
        try { repository.delete(m); repository.flush(); }
        catch (DataIntegrityViolationException e) { throw new IllegalStateException(
                "Manufacturer " + id + " cannot be deleted because it is referenced by a machine model", e); }
    }
    private Manufacturer find(Long id) { return repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Manufacturer " + id + " was not found")); }
    private void ensureName(String name, Long id) {
        boolean exists = id == null ? repository.existsByManufacturerNameIgnoreCase(name)
                : repository.existsByManufacturerNameIgnoreCaseAndManufacturerIdNot(name, id);
        if (exists) throw new IllegalStateException("Manufacturer '" + name + "' already exists");
    }
    private void apply(Manufacturer m, ManufacturerRequest r, String name, boolean create) {
        m.setManufacturerName(name);
        if (create) { m.setIsActive(r.isActive() == null || r.isActive()); m.setCreatedAt(LocalDateTime.now()); m.setCreatedBy(r.createdBy()); }
        else if (r.isActive() != null) m.setIsActive(r.isActive());
    }
    private ManufacturerResponse response(Manufacturer m) { return new ManufacturerResponse(m.getManufacturerId(),
            m.getManufacturerName(), m.isActive(), m.getCreatedAt(), m.getCreatedBy()); }
    private IllegalStateException duplicate(Exception e) { return new IllegalStateException("Manufacturer name already exists", e); }
}
