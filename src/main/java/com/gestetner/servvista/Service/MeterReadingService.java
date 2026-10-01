package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.meters.MeterReadingResponse;
import com.gestetner.servvista.Models.entity.machines.Machine;
import com.gestetner.servvista.Models.entity.meters.MeterReading;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.meters.MeterReadingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeterReadingService {

    private final MachineRepository machineRepository;
    private final MeterReadingRepository meterReadingRepository;

    public MeterReadingService(
            MachineRepository machineRepository,
            MeterReadingRepository meterReadingRepository) {
        this.machineRepository = machineRepository;
        this.meterReadingRepository = meterReadingRepository;
    }

    public List<MeterReadingResponse> getByMachineReferenceNumber(String referenceNumber) {
        String normalizedReference = referenceNumber.trim();
        Machine machine = machineRepository.findByMachineReferenceNumberIgnoreCase(normalizedReference)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Machine " + normalizedReference + " was not found"));

        return meterReadingRepository
                .findAllByMachineIdWithCounterTypeOrderByReadingDatetimeDesc(machine.getMachineId())
                .stream()
                .map(reading -> response(reading, machine.getMachineReferenceNumber()))
                .toList();
    }

    private MeterReadingResponse response(MeterReading reading, String machineReferenceNumber) {
        return new MeterReadingResponse(
                reading.getMeterReadingId(), reading.getMachineId(), machineReferenceNumber,
                reading.getMeterCounterTypeId(), reading.getMeterCounterType().getCounterCode(),
                reading.getMeterCounterType().getCounterName(), reading.getReadingValue(),
                reading.getReadingDatetime(), reading.getSourceCode(),
                reading.getInstallationSubmissionId(), reading.getServiceScheduleId(),
                reading.getBreakdownId(), reading.getCapturedByUserId(),
                reading.getCapturedByPortalAccountId(), reading.getNote(), reading.getCreatedAt());
    }
}
