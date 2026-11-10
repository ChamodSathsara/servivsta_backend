package com.gestetner.servvista.Models.entity.parts;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class PartCategoryMappingId implements Serializable {
    private Long partId;
    private Long partCategoryId;
}
