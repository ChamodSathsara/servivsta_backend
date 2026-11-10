package com.gestetner.servvista.Models.entity.parts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "part_category_mapping")
@IdClass(PartCategoryMappingId.class)
public class PartCategoryMapping {
    @Id @Column(name = "part_id")
    private Long partId;
    @Id @Column(name = "part_category_id")
    private Long partCategoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", insertable = false, updatable = false)
    private Part part;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_category_id", insertable = false, updatable = false)
    private PartCategory category;
}
