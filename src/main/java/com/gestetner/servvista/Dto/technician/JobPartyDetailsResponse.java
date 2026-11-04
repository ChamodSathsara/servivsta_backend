package com.gestetner.servvista.Dto.technician;

public record JobPartyDetailsResponse(
        JobCustomerDetailsResponse customer,
        JobSiteDetailsResponse site,
        JobSiteContactResponse primaryContact
) {
}
