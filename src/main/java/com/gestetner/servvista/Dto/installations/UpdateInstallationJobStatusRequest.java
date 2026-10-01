package com.gestetner.servvista.Dto.installations;

import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateInstallationJobStatusRequest(
        @NotNull InstallationJobStatus status,
        @NotNull @Positive Long performedBy,
        @Size(max = 255) String note
) {
}
