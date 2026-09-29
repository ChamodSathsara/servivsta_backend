package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.reference.NotificationEventTypeRequest;
import com.gestetner.servvista.Dto.reference.NotificationEventTypeResponse;
import com.gestetner.servvista.Models.entity.notifications.NotificationEventType;
import com.gestetner.servvista.Repositories.notifications.NotificationEventTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationEventTypeService {

    private final NotificationEventTypeRepository repository;

    public NotificationEventTypeService(NotificationEventTypeRepository repository) {
        this.repository = repository;
    }

    public NotificationEventTypeResponse create(NotificationEventTypeRequest request) {
        validateUnique(request, null);
        NotificationEventType eventType = new NotificationEventType();
        apply(eventType, request);
        return response(repository.saveAndFlush(eventType));
    }

    @Transactional(readOnly = true)
    public List<NotificationEventTypeResponse> getAll() {
        return repository.findAll(Sort.by("notificationEventTypeId"))
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public NotificationEventTypeResponse getById(Long id) {
        return response(find(id));
    }

    public NotificationEventTypeResponse update(
            Long id,
            NotificationEventTypeRequest request) {
        NotificationEventType eventType = find(id);
        validateUnique(request, id);
        apply(eventType, request);
        return response(repository.saveAndFlush(eventType));
    }

    public void delete(Long id) {
        NotificationEventType eventType = find(id);

        try {
            repository.delete(eventType);
            repository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Notification event type " + id
                            + " cannot be deleted because it is referenced by notifications",
                    exception);
        }
    }

    private void validateUnique(NotificationEventTypeRequest request, Long id) {
        boolean exists = id == null
                ? repository.existsByEventCode(request.eventCode())
                : repository.existsByEventCodeAndNotificationEventTypeIdNot(
                        request.eventCode(), id);

        if (exists) {
            throw new IllegalStateException(
                    "Notification event code " + request.eventCode() + " already exists");
        }
    }

    private void apply(
            NotificationEventType eventType,
            NotificationEventTypeRequest request) {
        eventType.setEventCode(request.eventCode());
        eventType.setDescription(optional(request.description()));
    }

    private NotificationEventType find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notification event type " + id + " was not found"));
    }

    private NotificationEventTypeResponse response(NotificationEventType eventType) {
        return new NotificationEventTypeResponse(
                eventType.getNotificationEventTypeId(),
                eventType.getEventCode(),
                eventType.getDescription());
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
