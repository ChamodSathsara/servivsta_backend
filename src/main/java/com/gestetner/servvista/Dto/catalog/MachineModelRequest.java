package com.gestetner.servvista.Dto.catalog;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MachineModelRequest(

        @NotNull
        Company company,

        @NotNull
        @Positive
        Long manufacturerId,

        @NotNull
        @Positive
        Long machineTypeId,

        @NotBlank
        @Size(max = 60)
        String modelNumber,

        @NotBlank
        @Size(max = 150)
        String modelName,

        @Size(max = 255)
        String description,

        Boolean isActive,

        @Positive
        Long createdBy
) {}
