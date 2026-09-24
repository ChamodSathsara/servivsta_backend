package com.gestetner.servvista.Models.entity.identity;

import com.gestetner.servvista.Models.Enums.Identity.CoordinatorRole;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coordinator")
public class Coordinator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinator_id", nullable = false, updatable = false)
    private Long coordinatorId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "coordinator_name", nullable = false, length = 150)
    private String coordinatorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "coordinator_role", nullable = false, length = 30)
    private CoordinatorRole coordinatorRole;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "division", length = 20)
    private Division division;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", length = 20)
    private Area area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
