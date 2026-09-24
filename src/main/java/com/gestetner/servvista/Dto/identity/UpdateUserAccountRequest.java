package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;

public interface UpdateUserAccountRequest {
    String userName();
    String mobileNumber();
    String email();
    String password();
    Boolean isActive();
    Division division();
    Area area();
    Long updatedBy();
}
