package com.gestetner.servvista.Repositories.feedback;


import com.gestetner.servvista.Models.entity.feedback.FieldServiceFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link FieldServiceFeedback}.
 */
@Repository
public interface FieldServiceFeedbackRepository extends JpaRepository<FieldServiceFeedback, Long> {
}
