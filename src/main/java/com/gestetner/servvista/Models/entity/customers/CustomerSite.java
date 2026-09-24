package com.gestetner.servvista.Models.entity.customers;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.entity.organization.City;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer_site", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "site_name"}))
public class CustomerSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_site_id", nullable = false, updatable = false)
    private Long customerSiteId;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "site_name", nullable = false, length = 150)
    private String siteName;
    @Column(name = "address_line_1", nullable = false, length = 150)
    private String addressLine1;
    @Column(name = "address_line_2", length = 150)
    private String addressLine2;
    @Column(name = "address_line_3", length = 150)
    private String addressLine3;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false, length = 20)
    private Area area;

    @Column(name = "city_id", nullable = false)
    private Long cityId;
    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;
    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;
    @Column(name = "is_head_office", nullable = false)
    private Boolean isHeadOffice;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;
}
