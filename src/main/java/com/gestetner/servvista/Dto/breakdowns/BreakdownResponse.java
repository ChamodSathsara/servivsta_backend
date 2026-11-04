package com.gestetner.servvista.Dto.breakdowns;

import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownReportedByType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record BreakdownResponse(
        Long breakdownId,
        String breakdownNumber,
        Long machineId,
        String machineReferenceNumber,
        Long customerSiteId,
        String siteName,
        String siteAddress,
        BigDecimal latitude,
        BigDecimal longitude,
        BreakdownReportedByType reportedByType,
        Long reportedByPortalAccountId,
        Long reportedByUserId,
        String reportedNote,
        Long informedSolutionTypeId,
        BreakdownStatus status,
        Long approvedBy,
        LocalDateTime approvedAt,
        String startNote,
        LocalDateTime startedAt,
        Long actualSolutionTypeId,
        String solutionNote,
        LocalDateTime completedAt,
        Long cancelledBy,
        LocalDateTime cancelledAt,
        String cancelReason,
        LocalDateTime createdAt,
        LocalDateTime expectedCompletionAt,
        String contactEmail
) {
}
