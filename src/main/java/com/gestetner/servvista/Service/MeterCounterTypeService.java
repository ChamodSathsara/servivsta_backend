package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.reference.MeterCounterTypeRequest;
import com.gestetner.servvista.Dto.reference.MeterCounterTypeResponse;
import com.gestetner.servvista.Models.entity.meters.MeterCounterType;
import com.gestetner.servvista.Repositories.meters.MeterCounterTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MeterCounterTypeService {

    private final MeterCounterTypeRepository repository;

    public MeterCounterTypeService(MeterCounterTypeRepository repository) {
        this.repository = repository;
    }

    public MeterCounterTypeResponse create(MeterCounterTypeRequest request) {
        validateUnique(request, null);
        MeterCounterType counterType = new MeterCounterType();
        apply(counterType, request, true);
        return response(repository.saveAndFlush(counterType));
    }

    @Transactional(readOnly = true)
    public List<MeterCounterTypeResponse> getAll() {
        return repository.findAll(Sort.by("meterCounterTypeId"))
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public MeterCounterTypeResponse getById(Long id) {
        return response(find(id));
    }

    public MeterCounterTypeResponse update(Long id, MeterCounterTypeRequest request) {
        MeterCounterType counterType = find(id);
        validateUnique(request, id);
        apply(counterType, request, false);
        return response(repository.saveAndFlush(counterType));
    }

    public void delete(Long id) {
        MeterCounterType counterType = find(id);

        try {
            repository.delete(counterType);
            repository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Meter counter type " + id
                            + " cannot be deleted because it is referenced by meter readings",
                    exception);
        }
    }

    private void validateUnique(MeterCounterTypeRequest request, Long id) {
        boolean exists = id == null
                ? repository.existsByCounterCode(request.counterCode())
                : repository.existsByCounterCodeAndMeterCounterTypeIdNot(
                        request.counterCode(), id);

        if (exists) {
            throw new IllegalStateException(
                    "Meter counter code " + request.counterCode() + " already exists");
        }
    }

    private void apply(
            MeterCounterType counterType,
            MeterCounterTypeRequest request,
            boolean create) {
        counterType.setCounterCode(request.counterCode());
        counterType.setCounterName(request.counterName().trim());

        if (create) {
            counterType.setIsActive(request.isActive() == null || request.isActive());
        } else if (request.isActive() != null) {
            counterType.setIsActive(request.isActive());
        }
    }

    private MeterCounterType find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Meter counter type " + id + " was not found"));
    }

    private MeterCounterTypeResponse response(MeterCounterType counterType) {
        return new MeterCounterTypeResponse(
                counterType.getMeterCounterTypeId(),
                counterType.getCounterCode(),
                counterType.getCounterName(),
                counterType.isActive());
    }
}
