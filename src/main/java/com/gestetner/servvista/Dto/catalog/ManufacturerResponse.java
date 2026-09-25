package com.gestetner.servvista.Dto.catalog;

import java.time.LocalDateTime;

public record ManufacturerResponse(Long manufacturerId, String manufacturerName,
                                   Boolean isActive, LocalDateTime createdAt, Long createdBy) {}
