package com.gestetner.servvista.Dto.technician;

public record TechnicianYtdPerformanceResponse(
        int year,
        PerformanceMetricResponse installationJobs,
        PerformanceMetricResponse breakdownJobs,
        PerformanceMetricResponse serviceVisits
) {
}
