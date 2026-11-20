package com.gestetner.servvista.Repositories.organization;

import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.entity.organization.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link City}.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByAreaAndCityNameIgnoreCase(Area area, String cityName);

    boolean existsByAreaAndCityNameIgnoreCaseAndCityIdNot(Area area, String cityName, Long cityId);

    Optional<City> findFirstByAreaAndCityNameIgnoreCase(Area area, String cityName);

}
