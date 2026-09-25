package com.gestetner.servvista.Dto.catalog;

public record MachineTypeResponse(Long machineTypeId, String machineTypeName,
                                  String machineTypeDescription, Boolean isActive) {}
