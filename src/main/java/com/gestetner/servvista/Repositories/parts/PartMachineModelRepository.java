package com.gestetner.servvista.Repositories.parts;

import com.gestetner.servvista.Models.entity.parts.PartMachineModel;
import com.gestetner.servvista.Models.entity.parts.PartMachineModelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for {@link PartMachineModel}.
 */
@Repository
public interface PartMachineModelRepository extends JpaRepository<PartMachineModel, PartMachineModelId> {
    List<PartMachineModel> findAllByPartIdOrderByModelId(Long partId);
    void deleteAllByPartId(Long partId);
}
