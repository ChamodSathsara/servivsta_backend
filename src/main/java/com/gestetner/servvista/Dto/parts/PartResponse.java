package com.gestetner.servvista.Dto.parts;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record PartResponse(Long partId, String partCode, String partName, String description,
                           BigDecimal unitPrice, Boolean isActive, Long createdBy,
                           LocalDateTime createdAt, List<Long> modelIds) {}
