package com.gestetner.servvista.Dto.technician;

public record PerformanceMetricResponse(
        long allJobs,
        long cancelledJobs,
        long eligibleJobs,
        long completedJobs,
        double percentage
) {
}
