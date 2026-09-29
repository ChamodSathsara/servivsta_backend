package com.gestetner.servvista.Dto.breakdowns;

import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownReportedByType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record BreakdownRequest(
        @NotNull @Positive Long machineId,
        @NotNull @Positive Long customerSiteId,
        @NotNull BreakdownReportedByType reportedByType,
        @Positive Long reportedByPortalAccountId,
        @Positive Long reportedByUserId,
        String reportedNote,
        @Positive Long informedSolutionTypeId,
        BreakdownStatus status,
        @Positive Long approvedBy,
        String startNote,
        @Positive Long actualSolutionTypeId,
        String solutionNote,
        @Positive Long cancelledBy,
        @Size(max = 255) String cancelReason,
        LocalDateTime expectedCompletionAt,
        @NotBlank @Email @Size(max = 150) String contactEmail
) {
}
