package com.gestetner.servvista.Dto.reference;

public record SolutionTypeResponse(
        Long solutionTypeId,
        String solutionCode,
        String solutionDescription,
        Boolean isActive
) {
}
