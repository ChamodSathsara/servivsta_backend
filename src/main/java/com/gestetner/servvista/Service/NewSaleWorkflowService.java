package com.gestetner.servvista.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestetner.servvista.Dto.installations.InstallationJobRequest;
import com.gestetner.servvista.Dto.installations.InstallationJobResponse;
import com.gestetner.servvista.Dto.installations.InstallationSubmissionRequest;
import com.gestetner.servvista.Dto.installations.CompleteInstallationJobRequest;
import com.gestetner.servvista.Dto.sales.NewSaleRequest;
import com.gestetner.servvista.Dto.sales.NewSaleResponse;
import com.gestetner.servvista.Dto.sales.NewSaleWorkflowDtos.*;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementStatus;
import com.gestetner.servvista.Models.Enums.Installations.InstallationJobStatus;
import com.gestetner.servvista.Models.Enums.Installations.InstallationVerificationStatus;
import com.gestetner.servvista.Models.Enums.Machines.*;
import com.gestetner.servvista.Models.Enums.Sales.NewSaleWorkflowStatus;
import com.gestetner.servvista.Models.entity.sales.NewSaleWorkflow;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.machines.MachineRepository;
import com.gestetner.servvista.Repositories.sales.NewSaleWorkflowRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service @Transactional
public class NewSaleWorkflowService {
    private final NewSaleWorkflowRepository repository;
    private final TechnicianRepository technicianRepository;
    private final MachineRepository machineRepository;
    private final InstallationService installationService;
    private final NewSaleService newSaleService;
    private final ObjectMapper objectMapper;

    public NewSaleWorkflowService(NewSaleWorkflowRepository repository,
            TechnicianRepository technicianRepository, MachineRepository machineRepository,
            InstallationService installationService, NewSaleService newSaleService,
            ObjectMapper objectMapper) {
        this.repository=repository; this.technicianRepository=technicianRepository;
        this.machineRepository=machineRepository; this.installationService=installationService;
        this.newSaleService=newSaleService; this.objectMapper=objectMapper;
    }

    public WorkflowResponse createFinance(FinanceStageRequest request) {
        if (request.warrantyEnd().isBefore(request.warrantyStart()))
            throw new IllegalArgumentException("Warranty end date cannot be before start date");
        NewSaleWorkflow workflow=new NewSaleWorkflow();
        workflow.setStatus(NewSaleWorkflowStatus.AWAITING_STORES);
        workflow.setFinancePayload(write(request)); workflow.setDespatchedFromStores(false);
        workflow.setCreatedBy(request.performedBy()); workflow.setCreatedAt(LocalDateTime.now());
        return response(repository.saveAndFlush(workflow));
    }

    @Transactional(readOnly=true)
    public List<WorkflowResponse> getAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(this::response).toList();
    }

    @Transactional(readOnly=true)
    public List<WorkflowResponse> getForTechnicianUser(Long userId) {
        Long technicianId=technicianRepository.findByUserId(userId)
                .orElseThrow(()->new EntityNotFoundException("Technician profile was not found"))
                .getTechnicianId();
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .filter(w->w.getStatus()==NewSaleWorkflowStatus.AWAITING_INSTALLATION)
                .filter(w->{CoordinatorStageRequest c=coordinator(w);return c!=null&&technicianId.equals(c.mainTechnicianId());})
                .map(this::response).toList();
    }

    public WorkflowResponse dispatch(Long id, StoresStageRequest request) {
        NewSaleWorkflow w=require(id, NewSaleWorkflowStatus.AWAITING_STORES);
        String reference=request.machineReferenceNumber().trim().toUpperCase();
        if (machineRepository.existsByMachineReferenceNumberIgnoreCase(reference))
            throw new IllegalStateException("Machine reference already exists: "+reference);
        w.setMachineReferenceNumber(reference); w.setDespatchedFromStores(true);
        w.setStatus(NewSaleWorkflowStatus.AWAITING_COORDINATOR); touch(w,request.performedBy());
        return response(repository.saveAndFlush(w));
    }

    public WorkflowResponse coordinate(Long id, CoordinatorStageRequest request) {
        NewSaleWorkflow w=require(id, NewSaleWorkflowStatus.AWAITING_COORDINATOR);
        if (request.mainTechnicianId().equals(request.serviceTechnicianId()))
            throw new IllegalArgumentException("Main and service technicians must be different");
        if (!technicianRepository.existsById(request.mainTechnicianId()) ||
                !technicianRepository.existsById(request.serviceTechnicianId()))
            throw new EntityNotFoundException("Selected technician was not found");
        FinanceStageRequest f=finance(w);
        InstallationJobResponse job=installationService.createJob(new InstallationJobRequest(
                f.company(),f.division(),f.customerId(),null,f.machineInvoiceId(),f.dealerId(),f.repId(),
                request.mainTechnicianId(),request.expectedInstallDate(),InstallationJobStatus.ASSIGNED,
                request.performedBy(),"Created from new-sale coordinator handoff"));
        w.setCoordinatorPayload(write(request)); w.setInstallationJobId(job.installationJobId());
        w.setStatus(NewSaleWorkflowStatus.AWAITING_INSTALLATION); touch(w,request.performedBy());
        return response(repository.saveAndFlush(w));
    }

    public NewSaleResponse completeInstallation(Long id, TechnicianStageRequest request) {
        NewSaleWorkflow w=require(id, NewSaleWorkflowStatus.AWAITING_INSTALLATION);
        FinanceStageRequest f=finance(w); CoordinatorStageRequest c=coordinator(w);
        if (!f.customerId().equals(request.customerSite().customerId()))
            throw new IllegalArgumentException("Installation site must belong to the selected customer");
        NewSaleRequest sale=new NewSaleRequest(
                new NewSaleRequest.MachineData(f.serialNumber(),f.company(),f.division(),f.modelId(),
                        MachineStatus.INSTALLATION_PENDING,f.machineInvoiceId(),f.dealerId(),f.repId(),null,
                        null,f.machineNote(),f.creditNoteNumber()),
                request.customerSite(),request.siteContact(),
                new NewSaleRequest.AssignmentData(f.customerId(),request.assignmentType(),request.assignedFrom()),
                new NewSaleRequest.TechnicianData(c.mainTechnicianId(),c.serviceTechnicianId(),c.techniciansAssignedFrom()),
                new NewSaleRequest.WarrantyData(f.warrantyType(),f.warrantyStart(),f.warrantyEnd(),
                        f.durationMonths(),WarrantyStatus.ACTIVE,f.warrantyNote()),
                new NewSaleRequest.LocationData(request.customerSite().latitude(),request.customerSite().longitude()),
                new NewSaleRequest.InstallationData(c.expectedInstallDate(),InstallationJobStatus.ASSIGNED,
                        "Installation details submitted by technician"),
                new NewSaleRequest.AgreementData(c.agreementType(),c.agreementStart(),c.agreementEnd(),
                        c.periodYears(),c.visitsPerYear(),zero(c.annualPayment()),zero(c.fullPayment()),
                        zero(c.discount()),zero(c.vatPercentage()),zero(c.vatAmount()),AgreementStatus.ACTIVE,
                        true,null,c.agreementNote(),"Created through role-based new sale"),
                request.performedBy(),f.statusReason());
        NewSaleResponse result=newSaleService.completeWorkflow(sale,w.getInstallationJobId(),w.getMachineReferenceNumber());
        InstallationSubmissionRequest submissionRequest=new InstallationSubmissionRequest(
                w.getInstallationJobId(),result.machineId(),f.modelId(),result.customerSiteId(),
                result.siteContactId(),request.assignedFrom(),null,c.agreementType(),f.warrantyNote(),
                request.performedBy(),InstallationVerificationStatus.VERIFIED,request.performedBy(),
                "Verified from technician installation form","Installation form submitted");
        var submission=installationService.createSubmission(submissionRequest);
        installationService.updateSubmission(submission.installationSubmissionId(),submissionRequest);
        installationService.completeJob(w.getInstallationJobId(),
                new CompleteInstallationJobRequest(request.performedBy(),"Installation completed"));
        w.setMachineId(result.machineId()); w.setStatus(NewSaleWorkflowStatus.COMPLETED); touch(w,request.performedBy());
        repository.saveAndFlush(w); return result;
    }

    private BigDecimal zero(BigDecimal value){return value==null?BigDecimal.ZERO:value;}
    private NewSaleWorkflow require(Long id,NewSaleWorkflowStatus status){NewSaleWorkflow w=repository.findById(id).orElseThrow(()->new EntityNotFoundException("New sale workflow "+id+" was not found"));if(w.getStatus()!=status)throw new IllegalStateException("Workflow is "+w.getStatus()+", expected "+status);return w;}
    private void touch(NewSaleWorkflow w,Long user){w.setUpdatedBy(user);w.setUpdatedAt(LocalDateTime.now());}
    private String write(Object value){try{return objectMapper.writeValueAsString(value);}catch(JsonProcessingException e){throw new IllegalStateException("Could not save workflow data",e);}}
    private <T>T read(String value,Class<T> type){try{return value==null?null:objectMapper.readValue(value,type);}catch(JsonProcessingException e){throw new IllegalStateException("Could not read workflow data",e);}}
    private FinanceStageRequest finance(NewSaleWorkflow w){return read(w.getFinancePayload(),FinanceStageRequest.class);}
    private CoordinatorStageRequest coordinator(NewSaleWorkflow w){return read(w.getCoordinatorPayload(),CoordinatorStageRequest.class);}
    private WorkflowResponse response(NewSaleWorkflow w){return new WorkflowResponse(w.getNewSaleWorkflowId(),w.getStatus(),finance(w),w.getMachineReferenceNumber(),w.getDespatchedFromStores(),coordinator(w),w.getInstallationJobId(),w.getMachineId(),w.getCreatedAt(),w.getUpdatedAt());}
}
