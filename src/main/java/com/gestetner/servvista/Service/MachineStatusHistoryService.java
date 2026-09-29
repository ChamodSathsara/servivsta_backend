package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.machines.MachineStatusHistoryRequest;
import com.gestetner.servvista.Dto.machines.MachineStatusHistoryResponse;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.machines.MachineStatusHistory;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.machines.MachineStatusHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MachineStatusHistoryService {

    private final MachineStatusHistoryRepository historyRepository;
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;

    public MachineStatusHistoryService(
            MachineStatusHistoryRepository historyRepository,
            MachineRepository machineRepository,
            UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
    }

    public MachineStatusHistoryResponse create(MachineStatusHistoryRequest request) {
        Machine machine = validateReferences(request);
        MachineStatusHistory history = new MachineStatusHistory();
        apply(history, request);
        history.setChangedAt(LocalDateTime.now());
        return response(historyRepository.saveAndFlush(history), machine);
    }

    @Transactional(readOnly = true)
    public List<MachineStatusHistoryResponse> getAll() {
        return historyRepository.findAllByOrderByChangedAtDesc()
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public MachineStatusHistoryResponse getById(Long historyId) {
        return response(find(historyId));
    }

    @Transactional(readOnly = true)
    public List<MachineStatusHistoryResponse> getByMachineId(Long machineId) {
        requireMachine(machineId);
        return historyRepository.findAllByMachineIdOrderByChangedAtDesc(machineId)
                .stream()
                .map(this::response)
                .toList();
    }

    public MachineStatusHistoryResponse update(
            Long historyId,
            MachineStatusHistoryRequest request) {
        MachineStatusHistory history = find(historyId);
        Machine machine = validateReferences(request);
        apply(history, request);
        history.setChangedAt(LocalDateTime.now());
        return response(historyRepository.saveAndFlush(history), machine);
    }

    public void delete(Long historyId) {
        MachineStatusHistory history = find(historyId);
        historyRepository.delete(history);
        historyRepository.flush();
    }

    private Machine validateReferences(MachineStatusHistoryRequest request) {
        Machine machine = requireMachine(request.machineId());

        if (request.changedBy() != null && !userRepository.existsById(request.changedBy())) {
            throw new EntityNotFoundException(
                    "User " + request.changedBy() + " was not found");
        }

        return machine;
    }

    private Machine requireMachine(Long machineId) {
        return machineRepository.findById(machineId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Machine " + machineId + " was not found"));
    }

    private MachineStatusHistory find(Long historyId) {
        return historyRepository.findById(historyId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Machine status history " + historyId + " was not found"));
    }

    private void apply(
            MachineStatusHistory history,
            MachineStatusHistoryRequest request) {
        history.setMachineId(request.machineId());
        history.setPreviousStatus(request.previousStatus());
        history.setNewStatus(request.newStatus());
        history.setChangedBy(request.changedBy());
        history.setReason(optional(request.reason()));
    }

    private MachineStatusHistoryResponse response(MachineStatusHistory history) {
        return response(history, requireMachine(history.getMachineId()));
    }

    private MachineStatusHistoryResponse response(
            MachineStatusHistory history,
            Machine machine) {
        return new MachineStatusHistoryResponse(
                history.getMachineStatusHistoryId(),
                history.getMachineId(),
                machine.getMachineReferenceNumber(),
                history.getPreviousStatus(),
                history.getNewStatus(),
                history.getChangedAt(),
                history.getChangedBy(),
                history.getReason());
    }

    private String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
