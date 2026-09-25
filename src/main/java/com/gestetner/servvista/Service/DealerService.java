package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.catalog.DealerRequest;
import com.gestetner.servvista.Dto.catalog.DealerResponse;
import com.gestetner.servvista.Models.entity.sales.Dealer;
import com.gestetner.servvista.Repositories.sales.DealerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service @Transactional
public class DealerService {
    private final DealerRepository repository;
    public DealerService(DealerRepository repository) { this.repository = repository; }

    public DealerResponse create(DealerRequest request) {
        Dealer dealer = new Dealer();
        apply(dealer, request, true);
        return response(repository.saveAndFlush(dealer));
    }
    @Transactional(readOnly = true)
    public List<DealerResponse> getAll() {
        return repository.findAll(Sort.by("dealerId")).stream().map(this::response).toList();
    }
    public DealerResponse update(Long id, DealerRequest request) {
        Dealer dealer = find(id); apply(dealer, request, false);
        return response(repository.saveAndFlush(dealer));
    }
    public void delete(Long id) {
        Dealer dealer = find(id);
        try { repository.delete(dealer); repository.flush(); }
        catch (DataIntegrityViolationException e) { throw referenced("Dealer", id, e); }
    }
    private Dealer find(Long id) { return repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Dealer " + id + " was not found")); }
    private void apply(Dealer d, DealerRequest r, boolean create) {
        d.setDealerName(r.dealerName().trim());
        d.setDealerAddress(optional(r.dealerAddress()));
        d.setDealerContactNumber(optional(r.dealerContactNumber()));
        if (create) { d.setIsActive(r.isActive() == null || r.isActive()); d.setCreatedAt(LocalDateTime.now()); d.setCreatedBy(r.createdBy()); }
        else if (r.isActive() != null) d.setIsActive(r.isActive());
    }
    private DealerResponse response(Dealer d) { return new DealerResponse(d.getDealerId(), d.getDealerName(),
            d.getDealerAddress(), d.getDealerContactNumber(), d.isActive(), d.getCreatedAt(), d.getCreatedBy()); }
    private String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private IllegalStateException referenced(String type, Long id, Exception e) { return new IllegalStateException(
            type + " " + id + " cannot be deleted because it is referenced by other records", e); }
}
