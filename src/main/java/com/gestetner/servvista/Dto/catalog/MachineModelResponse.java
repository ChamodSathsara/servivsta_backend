package com.gestetner.servvista.Dto.catalog;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import java.time.LocalDateTime;
import java.util.Set;

public record MachineModelResponse(
        Long modelId, Company company, Set<Company> companies, Long manufacturerId, String manufacturerName,
        Long machineTypeId, String machineTypeName, String modelNumber, String modelName,
        String description, Boolean isActive, LocalDateTime createdAt, Long createdBy
) {}
