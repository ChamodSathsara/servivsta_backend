package com.gestetner.servvista.Dto.technician;

public record JobSiteContactResponse(
        Long siteContactId,
        String contactName,
        String designation,
        String mobileNumber,
        String email
) {
}
