package com.gestetner.servvista.Dto.installations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CompleteInstallationJobRequest(
        @NotNull @Positive Long performedBy,
        @Size(max = 255) String note
) {
}
