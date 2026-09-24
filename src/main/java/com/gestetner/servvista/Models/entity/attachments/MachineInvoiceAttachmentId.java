package com.gestetner.servvista.Models.entity.attachments;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link MachineInvoiceAttachment}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class MachineInvoiceAttachmentId implements Serializable {

    private Long machineInvoiceId;
    private Long attachmentId;

    public MachineInvoiceAttachmentId() {
    }

    public MachineInvoiceAttachmentId(Long machineInvoiceId, Long attachmentId) {
        this.machineInvoiceId = machineInvoiceId;
        this.attachmentId = attachmentId;
    }

    public Long getMachineInvoiceId() { return machineInvoiceId; }
    public void setMachineInvoiceId(Long machineInvoiceId) { this.machineInvoiceId = machineInvoiceId; }

    public Long getAttachmentId() { return attachmentId; }
    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineInvoiceAttachmentId)) return false;
        MachineInvoiceAttachmentId that = (MachineInvoiceAttachmentId) o;
        return Objects.equals(machineInvoiceId, that.machineInvoiceId) && Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineInvoiceId, attachmentId);
    }
}
