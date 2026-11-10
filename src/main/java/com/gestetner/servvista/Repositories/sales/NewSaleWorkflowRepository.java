package com.gestetner.servvista.Repositories.sales;

import com.gestetner.servvista.Models.entity.sales.NewSaleWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewSaleWorkflowRepository extends JpaRepository<NewSaleWorkflow, Long> {
    List<NewSaleWorkflow> findAllByOrderByCreatedAtDesc();
}
