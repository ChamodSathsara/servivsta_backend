package com.gestetner.servvista.Dto.technician;

public record TechnicianEstimateMachineOption(
        Long machineId,
        String machineReferenceNumber,
        Long modelId,
        String modelNumber,
        String modelName) {
}
