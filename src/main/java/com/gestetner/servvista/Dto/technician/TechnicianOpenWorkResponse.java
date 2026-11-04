package com.gestetner.servvista.Dto.technician;

import com.gestetner.servvista.Dto.breakdowns.BreakdownResponse;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
import com.gestetner.servvista.Dto.services.ServiceScheduleResponse;

import java.util.List;

public record TechnicianOpenWorkResponse(
        List<InstallationJobResponse> installationJobs,
        List<BreakdownResponse> breakdownJobs,
        List<ServiceScheduleResponse> serviceVisits
) {
}
