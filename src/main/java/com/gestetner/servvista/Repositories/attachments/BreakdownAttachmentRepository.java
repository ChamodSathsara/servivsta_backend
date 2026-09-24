package com.gestetner.servvista.Repositories.attachments;


import com.gestetner.servvista.Models.entity.attachments.BreakdownAttachment;
import com.gestetner.servvista.Models.entity.attachments.BreakdownAttachmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link BreakdownAttachment}.
 */
@Repository
public interface BreakdownAttachmentRepository extends JpaRepository<BreakdownAttachment, BreakdownAttachmentId> {
}
