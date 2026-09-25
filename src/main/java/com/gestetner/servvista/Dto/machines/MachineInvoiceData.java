package com.gestetner.servvista.Dto.machines;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MachineInvoiceData(
        @NotBlank @Size(max = 40) String invoiceNumber,
        @Size(max = 40) String belitaInvoiceNumber,
        LocalDate invoiceDate,
        String note
) {
}
