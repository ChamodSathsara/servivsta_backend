package com.gestetner.servvista.Dto.estimates;

import com.gestetner.servvista.Dto.catalog.MachineModelResponse;
import com.gestetner.servvista.Dto.identity.TechnicianUserResponse;
import com.gestetner.servvista.Dto.machines.MachineResponse;
import com.gestetner.servvista.Dto.parts.PartCategoryResponse;
import com.gestetner.servvista.Dto.parts.PartResponse;
import java.util.List;

public record EstimateOptionsResponse(
        List<MachineResponse> machines,
        List<TechnicianUserResponse> technicians,
        List<MachineModelResponse> models,
        List<PartCategoryResponse> categories,
        List<PartResponse> parts) {
}
