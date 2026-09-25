package com.gestetner.servvista.Repositories.machines;


import com.gestetner.servvista.Models.entity.machines.MachineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link MachineModel}.
 */
@Repository
public interface MachineModelRepository extends JpaRepository<MachineModel, Long> {
    @Query("select model from MachineModel model join fetch model.manufacturer join fetch model.machineType order by model.modelId")
    List<MachineModel> findAllWithReferences();

    @Query("select model from MachineModel model join fetch model.manufacturer join fetch model.machineType where model.modelId = :modelId")
    Optional<MachineModel> findByIdWithReferences(Long modelId);
}
