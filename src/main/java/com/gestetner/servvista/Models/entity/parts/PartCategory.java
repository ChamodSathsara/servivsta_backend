package com.gestetner.servvista.Models.entity.parts;

import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "part_category", uniqueConstraints = @UniqueConstraint(columnNames = "category_code"))
public class PartCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_category_id", nullable = false, updatable = false)
    private Long partCategoryId;
    @Column(name = "category_code", nullable = false, length = 40)
    private String categoryCode;
    @Column(name = "category_name", nullable = false, length = 150)
    private String categoryName;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private Long createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByRef;
}
