package com.gestetner.servvista.Dto.identity;

import com.gestetner.servvista.Models.Enums.Organization.Company;

import java.util.List;

public interface CompanyAssignmentRequest extends UserAccountRequest {
    List<Company> companies();
}
