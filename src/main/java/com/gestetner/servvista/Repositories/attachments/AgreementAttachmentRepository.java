package com.gestetner.servvista.Repositories.attachments;


import com.gestetner.servvista.Models.entity.attachments.AgreementAttachment;
import com.gestetner.servvista.Models.entity.attachments.AgreementAttachmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link AgreementAttachment}.
 */
@Repository
public interface AgreementAttachmentRepository extends JpaRepository<AgreementAttachment, AgreementAttachmentId> {
}
