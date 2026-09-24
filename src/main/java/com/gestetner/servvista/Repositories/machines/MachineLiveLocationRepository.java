package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineLiveLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineLiveLocation}.
 */
@Repository
public interface MachineLiveLocationRepository extends JpaRepository<MachineLiveLocation, Long> {
}
