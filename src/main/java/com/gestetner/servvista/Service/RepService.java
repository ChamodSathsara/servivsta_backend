package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.catalog.RepRequest;
import com.gestetner.servvista.Dto.catalog.RepResponse;
import com.gestetner.servvista.Models.entity.sales.Rep;
import com.gestetner.servvista.Repositories.sales.RepRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service @Transactional
public class RepService {
    private final RepRepository repository;
    public RepService(RepRepository repository) { this.repository = repository; }

    public RepResponse create(RepRequest request) {
        String code = code(request.repCode()); ensureCode(code, null);
        try { Rep rep = new Rep(); apply(rep, request, code, true); return response(repository.saveAndFlush(rep)); }
        catch (DataIntegrityViolationException e) { throw duplicate(e); }
    }
    @Transactional(readOnly = true)
    public List<RepResponse> getAll() { return repository.findAll(Sort.by("repId")).stream().map(this::response).toList(); }
    public RepResponse update(Long id, RepRequest request) {
        Rep rep = find(id); String code = code(request.repCode()); ensureCode(code, id);
        try { apply(rep, request, code, false); return response(repository.saveAndFlush(rep)); }
        catch (DataIntegrityViolationException e) { throw duplicate(e); }
    }
    public void delete(Long id) {
        Rep rep = find(id);
        try { repository.delete(rep); repository.flush(); }
        catch (DataIntegrityViolationException e) { throw new IllegalStateException(
                "Rep " + id + " cannot be deleted because it is referenced by other records", e); }
    }
    private Rep find(Long id) { return repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Rep " + id + " was not found")); }
    private void ensureCode(String code, Long id) {
        boolean exists = id == null ? repository.existsByRepCodeIgnoreCase(code)
                : repository.existsByRepCodeIgnoreCaseAndRepIdNot(code, id);
        if (exists) throw new IllegalStateException("A rep with code '" + code + "' already exists");
    }
    private void apply(Rep rep, RepRequest r, String code, boolean create) {
        rep.setRepCode(code); rep.setRepName(r.repName().trim()); rep.setRepMobileNumber(optional(r.repMobileNumber()));
        if (create) { rep.setIsActive(r.isActive() == null || r.isActive()); rep.setCreatedAt(LocalDateTime.now()); rep.setCreatedBy(r.createdBy()); }
        else if (r.isActive() != null) rep.setIsActive(r.isActive());
    }
    private RepResponse response(Rep r) { return new RepResponse(r.getRepId(), r.getRepCode(), r.getRepName(),
            r.getRepMobileNumber(), r.isActive(), r.getCreatedAt(), r.getCreatedBy()); }
    private String code(String value) { return value.trim().toUpperCase(Locale.ROOT); }
    private String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private IllegalStateException duplicate(Exception e) { return new IllegalStateException("A rep with the supplied code already exists", e); }
}
