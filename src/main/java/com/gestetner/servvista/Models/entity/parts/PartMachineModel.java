package com.gestetner.servvista.Models.entity.parts;

import com.gestetner.servvista.Models.entity.machines.MachineModel;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "part_machine_model")
@IdClass(PartMachineModelId.class)
public class PartMachineModel {

    @Id
    @Column(name = "part_id")
    private Long partId;

    @Id
    @Column(name = "model_id")
    private Long modelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", insertable = false, updatable = false)
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", insertable = false, updatable = false)
    private MachineModel model;

    public PartMachineModel() {
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public MachineModel getModel() {
        return model;
    }

    public void setModel(MachineModel model) {
        this.model = model;
    }

}
