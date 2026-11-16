package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.sales.NewSaleResponse;
import com.gestetner.servvista.Dto.sales.NewSaleWorkflowDtos.*;
import com.gestetner.servvista.Service.NewSaleWorkflowService;
import com.gestetner.servvista.Dto.catalog.*;
import com.gestetner.servvista.Dto.customers.*;
import com.gestetner.servvista.Dto.machines.MachineInvoiceRequest;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
    private final com.gestetner.servvista.Service.ManufacturerService manufacturerService;
    private final com.gestetner.servvista.Service.MachineTypeService machineTypeService;
    private final com.gestetner.servvista.Service.NewSaleExcelService excelService;
    public NewSaleWorkflowController(NewSaleWorkflowService service,
            com.gestetner.servvista.Service.MachineModelService machineModelService,
            com.gestetner.servvista.Service.MachineInvoiceService machineInvoiceService,
            com.gestetner.servvista.Service.CustomerService customerService,
            com.gestetner.servvista.Service.DealerService dealerService,
            com.gestetner.servvista.Service.RepService repService,
            com.gestetner.servvista.Service.StaffUserService staffUserService,
            com.gestetner.servvista.Service.ManufacturerService manufacturerService,
            com.gestetner.servvista.Service.MachineTypeService machineTypeService,
            com.gestetner.servvista.Service.NewSaleExcelService excelService){this.service=service;this.machineModelService=machineModelService;this.machineInvoiceService=machineInvoiceService;this.customerService=customerService;this.dealerService=dealerService;this.repService=repService;this.staffUserService=staffUserService;this.manufacturerService=manufacturerService;this.machineTypeService=machineTypeService;this.excelService=excelService;}

    @GetMapping(value="/finance/template",produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<byte[]> financeTemplate(){return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=finance-new-sales-template.xlsx").body(excelService.template());}

    @PostMapping(value="/finance/import",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public com.gestetner.servvista.Service.NewSaleExcelService.ImportResult financeImport(@RequestPart("file") MultipartFile file,@RequestParam Long performedBy){return excelService.importFile(file,performedBy);}

    @GetMapping("/options")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE','STORES_USER','COORDINATOR')")
    public WorkflowOptionsResponse options(){return new WorkflowOptionsResponse(machineModelService.getAll(),machineInvoiceService.getAll(),customerService.getAll(),dealerService.getAll(),repService.getAll(),staffUserService.getTechnicians(),manufacturerService.getAll(),machineTypeService.getAll(),staffUserService.getSalesmen());}

    @PostMapping("/finance")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<WorkflowResponse> finance(@Valid @RequestBody FinanceStageRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(service.createFinance(request));}

    @PostMapping("/options/models")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<MachineModelResponse> createModel(@Valid @RequestBody MachineModelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineModelService.create(request));
    }

    @PostMapping("/options/invoices")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<com.gestetner.servvista.Dto.machines.MachineInvoiceResponse> createInvoice(@Valid @RequestBody MachineInvoiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineInvoiceService.create(request));
    }

    @PostMapping("/options/dealers")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<DealerResponse> createDealer(@Valid @RequestBody DealerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dealerService.create(request));
    }

    @PostMapping("/options/representatives")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<RepResponse> createRepresentative(@Valid @RequestBody RepRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repService.create(request));
    }

    @PostMapping("/options/customers")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request));
    }

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
