package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.reference.SolutionTypeRequest;
import com.gestetner.servvista.Dto.reference.SolutionTypeResponse;
import com.gestetner.servvista.Models.entity.services.SolutionType;
import com.gestetner.servvista.Repositories.services.SolutionTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class SolutionTypeService {

    private final SolutionTypeRepository repository;

    public SolutionTypeService(SolutionTypeRepository repository) {
        this.repository = repository;
    }

    public SolutionTypeResponse create(SolutionTypeRequest request) {
        String code = normalizeCode(request.solutionCode());
        validateUnique(code, null);

        SolutionType solutionType = new SolutionType();
        apply(solutionType, request, code, true);
        return response(repository.saveAndFlush(solutionType));
    }

    @Transactional(readOnly = true)
    public List<SolutionTypeResponse> getAll() {
        return repository.findAll(Sort.by("solutionTypeId"))
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public SolutionTypeResponse getById(Long id) {
        return response(find(id));
    }

    public SolutionTypeResponse update(Long id, SolutionTypeRequest request) {
        SolutionType solutionType = find(id);
        String code = normalizeCode(request.solutionCode());
        validateUnique(code, id);
        apply(solutionType, request, code, false);
        return response(repository.saveAndFlush(solutionType));
    }

    public void delete(Long id) {
        SolutionType solutionType = find(id);

        try {
            repository.delete(solutionType);
            repository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw referenced("Solution type", id, exception);
        }
    }

    private void validateUnique(String code, Long id) {
        boolean exists = id == null
                ? repository.existsBySolutionCodeIgnoreCase(code)
                : repository.existsBySolutionCodeIgnoreCaseAndSolutionTypeIdNot(code, id);

        if (exists) {
            throw new IllegalStateException("Solution code " + code + " already exists");
        }
    }

    private void apply(
            SolutionType solutionType,
            SolutionTypeRequest request,
            String code,
            boolean create) {
        solutionType.setSolutionCode(code);
        solutionType.setSolutionDescription(request.solutionDescription().trim());

        if (create) {
            solutionType.setIsActive(request.isActive() == null || request.isActive());
        } else if (request.isActive() != null) {
            solutionType.setIsActive(request.isActive());
        }
    }

    private SolutionType find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Solution type " + id + " was not found"));
    }

    private SolutionTypeResponse response(SolutionType solutionType) {
        return new SolutionTypeResponse(
                solutionType.getSolutionTypeId(),
                solutionType.getSolutionCode(),
                solutionType.getSolutionDescription(),
                solutionType.isActive());
    }

    private String normalizeCode(String code) {
        return code.trim().toUpperCase(Locale.ROOT);
    }

    private IllegalStateException referenced(String resource, Long id, Exception exception) {
        return new IllegalStateException(
                resource + " " + id + " cannot be deleted because it is referenced by other records",
                exception);
    }
}
