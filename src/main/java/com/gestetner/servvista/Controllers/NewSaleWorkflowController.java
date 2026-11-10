package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.sales.NewSaleResponse;
import com.gestetner.servvista.Dto.sales.NewSaleWorkflowDtos.*;
import com.gestetner.servvista.Service.NewSaleWorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/new-sales/workflow")
public class NewSaleWorkflowController {
    private final NewSaleWorkflowService service;
    private final com.gestetner.servvista.Service.MachineModelService machineModelService;
    private final com.gestetner.servvista.Service.MachineInvoiceService machineInvoiceService;
    private final com.gestetner.servvista.Service.CustomerService customerService;
    private final com.gestetner.servvista.Service.DealerService dealerService;
    private final com.gestetner.servvista.Service.RepService repService;
    private final com.gestetner.servvista.Service.StaffUserService staffUserService;
    public NewSaleWorkflowController(NewSaleWorkflowService service,
            com.gestetner.servvista.Service.MachineModelService machineModelService,
            com.gestetner.servvista.Service.MachineInvoiceService machineInvoiceService,
            com.gestetner.servvista.Service.CustomerService customerService,
            com.gestetner.servvista.Service.DealerService dealerService,
            com.gestetner.servvista.Service.RepService repService,
            com.gestetner.servvista.Service.StaffUserService staffUserService){this.service=service;this.machineModelService=machineModelService;this.machineInvoiceService=machineInvoiceService;this.customerService=customerService;this.dealerService=dealerService;this.repService=repService;this.staffUserService=staffUserService;}

    @GetMapping("/options")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE','STORES_USER','COORDINATOR')")
    public WorkflowOptionsResponse options(){return new WorkflowOptionsResponse(machineModelService.getAll(),machineInvoiceService.getAll(),customerService.getAll(),dealerService.getAll(),repService.getAll(),staffUserService.getTechnicians());}

    @PostMapping("/finance")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<WorkflowResponse> finance(@Valid @RequestBody FinanceStageRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(service.createFinance(request));}

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE','STORES_USER','COORDINATOR','TECHNICIAN')")
    public List<WorkflowResponse> all(){return service.getAll();}

    @PostMapping("/{id}/stores")
    @PreAuthorize("hasAnyRole('ADMIN','STORES_USER')")
    public WorkflowResponse stores(@PathVariable Long id,@Valid @RequestBody StoresStageRequest request){return service.dispatch(id,request);}

    @PostMapping("/{id}/coordinator")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    public WorkflowResponse coordinator(@PathVariable Long id,@Valid @RequestBody CoordinatorStageRequest request){return service.coordinate(id,request);}

    @PostMapping("/{id}/installation")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN')")
    public NewSaleResponse installation(@PathVariable Long id,@Valid @RequestBody TechnicianStageRequest request){return service.completeInstallation(id,request);}
}
