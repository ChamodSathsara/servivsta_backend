package com.gestetner.servvista.Models.entity.parts;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link PartMachineModel}.
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
public class PartMachineModelId implements Serializable {

    private Long partId;
    private Long modelId;

    public PartMachineModelId() {
    }

    public PartMachineModelId(Long partId, Long modelId) {
        this.partId = partId;
        this.modelId = modelId;
    }

    public Long getPartId() { return partId; }
    public void setPartId(Long partId) { this.partId = partId; }

    public Long getModelId() { return modelId; }
    public void setModelId(Long modelId) { this.modelId = modelId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartMachineModelId)) return false;
        PartMachineModelId that = (PartMachineModelId) o;
        return Objects.equals(partId, that.partId) && Objects.equals(modelId, that.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, modelId);
    }
}
