package com.gestetner.servvista.Repositories.identity;

import com.gestetner.servvista.Models.entity.identity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    Optional<Technician> findByUserId(Long userId);
    Optional<Technician> findByTechCode(String techCode);

    @Query("select technician from Technician technician join fetch technician.user order by technician.technicianId")
    List<Technician> findAllWithUser();
}
