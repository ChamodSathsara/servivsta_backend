package com.gestetner.servvista.Dto.technician;

public record JobCustomerDetailsResponse(
        Long customerId,
        String sageCode,
        String customerName,
        String address,
        String telephone,
        String email,
        String grade,
        String type,
        String segment
) {
}
