package com.gestetner.servvista.Dto.breakdowns;

public record BreakdownCreateResponse(
        BreakdownResponse breakdown,
        BreakdownAssignmentResponse technicianAssignment
) {
}
