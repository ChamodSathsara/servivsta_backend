package com.gestetner.servvista.Repositories.feedback;


import com.gestetner.servvista.Models.entity.feedback.FieldServiceFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link FieldServiceFeedback}.
 */
@Repository
public interface FieldServiceFeedbackRepository extends JpaRepository<FieldServiceFeedback, Long> {

    boolean existsByBreakdownId(Long breakdownId);

    boolean existsByBreakdownIdAndFeedbackIdNot(Long breakdownId, Long feedbackId);

    Optional<FieldServiceFeedback> findByBreakdownId(Long breakdownId);

    void deleteAllByBreakdownId(Long breakdownId);
}
