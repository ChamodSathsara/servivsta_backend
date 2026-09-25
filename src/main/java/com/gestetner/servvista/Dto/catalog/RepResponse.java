package com.gestetner.servvista.Dto.catalog;

import java.time.LocalDateTime;

public record RepResponse(Long repId, String repCode, String repName,
                          String repMobileNumber, Boolean isActive,
                          LocalDateTime createdAt, Long createdBy) {}
