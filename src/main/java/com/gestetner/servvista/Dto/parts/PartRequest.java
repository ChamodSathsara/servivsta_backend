package com.gestetner.servvista.Dto.parts;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record PartRequest(@NotBlank @Size(max=40) String partCode,
                          @NotBlank @Size(max=150) String partName,
                          @Size(max=255) String description,
                          @PositiveOrZero BigDecimal unitPrice,
                          Boolean isActive,
                          @Positive Long createdBy,
                          @NotEmpty List<@Positive Long> modelIds) {}
