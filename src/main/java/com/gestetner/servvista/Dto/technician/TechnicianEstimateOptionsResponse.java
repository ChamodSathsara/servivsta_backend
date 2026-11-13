package com.gestetner.servvista.Dto.technician;

import com.gestetner.servvista.Dto.catalog.MachineModelResponse;
import com.gestetner.servvista.Dto.parts.PartCategoryResponse;
import com.gestetner.servvista.Dto.parts.PartResponse;
import java.util.List;

public record TechnicianEstimateOptionsResponse(
        List<TechnicianEstimateMachineOption> machines,
        List<MachineModelResponse> models,
        List<PartCategoryResponse> categories,
        List<PartResponse> parts) {
}
