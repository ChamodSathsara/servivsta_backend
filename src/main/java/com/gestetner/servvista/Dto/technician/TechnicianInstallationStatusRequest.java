package com.gestetner.servvista.Dto.technician;

import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TechnicianInstallationStatusRequest(
        @NotNull InstallationJobStatus status,
        @Size(max = 255) String note
) {
}
