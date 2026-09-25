package com.gestetner.servvista.Dto.catalog;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.time.LocalDateTime;

public record MachineModelResponse(
        Long modelId, Company company, Long manufacturerId, String manufacturerName,
        Long machineTypeId, String machineTypeName, String modelNumber, String modelName,
        String description, Boolean isActive, LocalDateTime createdAt, Long createdBy
) {}
