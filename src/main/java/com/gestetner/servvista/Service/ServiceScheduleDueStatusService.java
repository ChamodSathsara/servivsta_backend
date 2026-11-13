package com.gestetner.servvista.Service;

import com.gestetner.servvista.Repositories.services.ServiceScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ServiceScheduleDueStatusService {
    private final ServiceScheduleRepository repository;

    public ServiceScheduleDueStatusService(ServiceScheduleRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int refreshDueStatuses() {
        return repository.markScheduledVisitsDue(LocalDate.now());
    }
}
