package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.parts.PartCategoryRequest;
import com.gestetner.servvista.Dto.parts.PartCategoryResponse;
import com.gestetner.servvista.Models.entity.parts.PartCategory;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.parts.PartCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class PartCategoryService {
    private final PartCategoryRepository repository;
    private final UserRepository users;

    public PartCategoryService(PartCategoryRepository repository, UserRepository users) {
        this.repository = repository;
        this.users = users;
    }

    public PartCategoryResponse create(PartCategoryRequest request) {
        validate(request, null);
        PartCategory category = new PartCategory();
        apply(category, request, true);
        return response(repository.saveAndFlush(category));
    }

    @Transactional(readOnly = true)
    public List<PartCategoryResponse> getAll() {
        return repository.findAll(Sort.by("partCategoryId")).stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public PartCategoryResponse getById(Long id) { return response(find(id)); }

    public PartCategoryResponse update(Long id, PartCategoryRequest request) {
        PartCategory category = find(id);
        validate(request, id);
        apply(category, request, false);
        return response(repository.saveAndFlush(category));
    }

    public void delete(Long id) {
        PartCategory category = find(id);
        try { repository.delete(category); repository.flush(); }
        catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException("Part category " + id + " is assigned to one or more parts", exception);
        }
    }

    private void validate(PartCategoryRequest request, Long id) {
        String code = code(request.categoryCode());
        boolean exists = id == null ? repository.existsByCategoryCodeIgnoreCase(code)
                : repository.existsByCategoryCodeIgnoreCaseAndPartCategoryIdNot(code, id);
        if (exists) throw new IllegalStateException("Part category code " + code + " already exists");
        if (request.createdBy() != null && !users.existsById(request.createdBy())) {
            throw new EntityNotFoundException("User " + request.createdBy() + " was not found");
        }
    }

    private void apply(PartCategory category, PartCategoryRequest request, boolean create) {
        category.setCategoryCode(code(request.categoryCode()));
        category.setCategoryName(request.categoryName().trim());
        category.setDescription(optional(request.description()));
        if (create) {
            category.setIsActive(request.isActive() == null || request.isActive());
            category.setCreatedBy(request.createdBy());
            category.setCreatedAt(LocalDateTime.now());
        } else if (request.isActive() != null) category.setIsActive(request.isActive());
    }

    private PartCategory find(Long id) { return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Part category " + id + " was not found")); }
    private PartCategoryResponse response(PartCategory value) { return new PartCategoryResponse(
            value.getPartCategoryId(), value.getCategoryCode(), value.getCategoryName(),
            value.getDescription(), value.getIsActive(), value.getCreatedBy(), value.getCreatedAt()); }
    private String code(String value) { return value.trim().toUpperCase(Locale.ROOT); }
    private String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
