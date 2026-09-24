package com.gestetner.servvista.Models.entity.organization;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city", uniqueConstraints = @UniqueConstraint(columnNames = {"area", "city_name"}))
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false, updatable = false)
    private Long cityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false, length = 20)
    private Area area;

    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
