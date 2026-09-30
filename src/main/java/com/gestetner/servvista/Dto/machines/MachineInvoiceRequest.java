package com.gestetner.servvista.Dto.machines;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MachineInvoiceRequest(
        @NotBlank @Size(max = 40) String invoiceNumber,
        @Size(max = 40) String belitaInvoiceNumber,
        LocalDate invoiceDate,
        @NotNull @Positive Long customerId,
        String note,
        @Positive Long createdBy
) {
}
