package com.gestetner.servvista.Repositories.services;

import com.gestetner.servvista.Models.entity.services.SolutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link SolutionType}.
 */
@Repository
public interface SolutionTypeRepository extends JpaRepository<SolutionType, Long> {
}
