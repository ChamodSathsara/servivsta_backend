package com.gestetner.servvista.Models.entity.services;

import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "solution_type")
public class SolutionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_type_id", nullable = false, updatable = false)
    private Long solutionTypeId;

    @Column(name = "solution_code", nullable = false)
    private String solutionCode;

    @Column(name = "solution_description", nullable = false)
    private String solutionDescription;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public SolutionType() {
    }

    public Long getSolutionTypeId() {
        return solutionTypeId;
    }

    public void setSolutionTypeId(Long solutionTypeId) {
        this.solutionTypeId = solutionTypeId;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
