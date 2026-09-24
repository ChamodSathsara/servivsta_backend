package com.gestetner.servvista.Models.entity.identity;

import com.gestetner.servvista.Models.Enums.Identity.TechnicianRole;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "technician")
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technician_id", nullable = false, updatable = false)
    private Long technicianId;

    @Column(name = "tech_code", nullable = false, unique = true, length = 20)
    private String techCode;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "technician_name", nullable = false, length = 150)
    private String technicianName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "division", nullable = false, length = 20)
    private Division division;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false, length = 20)
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(name = "technician_role", nullable = false, length = 30)
    private TechnicianRole technicianRole;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
