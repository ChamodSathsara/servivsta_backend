package com.gestetner.servvista.Repositories.parts;

import com.gestetner.servvista.Models.entity.parts.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Part}.
 */
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
