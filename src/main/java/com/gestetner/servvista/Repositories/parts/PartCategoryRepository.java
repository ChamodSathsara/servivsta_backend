package com.gestetner.servvista.Repositories.parts;

import com.gestetner.servvista.Models.entity.parts.PartCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartCategoryRepository extends JpaRepository<PartCategory, Long> {
    boolean existsByCategoryCodeIgnoreCase(String categoryCode);
    boolean existsByCategoryCodeIgnoreCaseAndPartCategoryIdNot(String categoryCode, Long id);
}
