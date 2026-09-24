package com.gestetner.servvista.Models.entity.attachments;

import com.gestetner.servvista.Models.entity.machines.MachineInvoice;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "machine_invoice_attachment")
@IdClass(MachineInvoiceAttachmentId.class)
public class MachineInvoiceAttachment {

    @Id
    @Column(name = "machine_invoice_id")
    private Long machineInvoiceId;

    @Id
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_invoice_id", insertable = false, updatable = false)
    private MachineInvoice machineInvoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", insertable = false, updatable = false)
    private Attachment attachment;

    public MachineInvoiceAttachment() {
    }

    public Long getMachineInvoiceId() {
        return machineInvoiceId;
    }

    public void setMachineInvoiceId(Long machineInvoiceId) {
        this.machineInvoiceId = machineInvoiceId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public MachineInvoice getMachineInvoice() {
        return machineInvoice;
    }

    public void setMachineInvoice(MachineInvoice machineInvoice) {
        this.machineInvoice = machineInvoice;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
