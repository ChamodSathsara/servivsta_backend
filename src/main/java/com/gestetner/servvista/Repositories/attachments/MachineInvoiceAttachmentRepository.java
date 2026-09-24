package com.gestetner.servvista.Repositories.attachments;


import com.gestetner.servvista.Models.entity.attachments.MachineInvoiceAttachment;
import com.gestetner.servvista.Models.entity.attachments.MachineInvoiceAttachmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link MachineInvoiceAttachment}.
 */
@Repository
public interface MachineInvoiceAttachmentRepository extends JpaRepository<MachineInvoiceAttachment, MachineInvoiceAttachmentId> {
}
