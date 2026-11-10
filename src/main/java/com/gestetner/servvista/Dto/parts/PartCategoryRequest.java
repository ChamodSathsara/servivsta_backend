package com.gestetner.servvista.Dto.parts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PartCategoryRequest(
        @NotBlank @Size(max = 40) String categoryCode,
        @NotBlank @Size(max = 150) String categoryName,
        @Size(max = 255) String description,
        Boolean isActive,
        @Positive Long createdBy
) {
}
