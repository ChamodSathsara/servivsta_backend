package com.gestetner.servvista.Models.entity.organization;

import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.entity.identity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_company", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "company"}))
public class UserCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_company_id", nullable = false, updatable = false)
    private Long userCompanyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "company", nullable = false, length = 20)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
