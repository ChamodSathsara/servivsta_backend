package com.gestetner.servvista.Repositories.machines;

import com.gestetner.servvista.Models.entity.machines.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Manufacturer}.
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    boolean existsByManufacturerNameIgnoreCase(String manufacturerName);
    boolean existsByManufacturerNameIgnoreCaseAndManufacturerIdNot(String manufacturerName, Long manufacturerId);
}
