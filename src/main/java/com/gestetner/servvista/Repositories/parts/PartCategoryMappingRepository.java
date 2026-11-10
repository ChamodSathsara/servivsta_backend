package com.gestetner.servvista.Repositories.parts;

import com.gestetner.servvista.Models.entity.parts.PartCategoryMapping;
import com.gestetner.servvista.Models.entity.parts.PartCategoryMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartCategoryMappingRepository extends JpaRepository<PartCategoryMapping, PartCategoryMappingId> {
    List<PartCategoryMapping> findAllByPartIdOrderByPartCategoryId(Long partId);
    void deleteAllByPartId(Long partId);
}
