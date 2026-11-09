package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.reports.ServiceVisitReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import com.gestetner.servvista.Service.ServiceVisitReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports/service-visits")
@Tag(name = "Reports")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
public class ServiceVisitReportController {
    private static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final ServiceVisitReportService service;

    public ServiceVisitReportController(ServiceVisitReportService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Get the paginated service visit report")
    public Page<ServiceVisitReportRow> report(Filter filter) {
        return service.report(filter.startDate(), filter.endDate(), filter.company(), filter.division(), filter.area(), filter.agreementType(),
                filter.visitStatus(), filter.search(), filter.pageNumber(), filter.pageSize());
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(Filter filter) {
        return download(service.pdf(filter.startDate(), filter.endDate(), filter.company(), filter.division(), filter.area(), filter.agreementType(),
                filter.visitStatus(), filter.search(), filter.pageNumber(), filter.pageSize()),
                MediaType.APPLICATION_PDF, "service-visit-report.pdf");
    }

    @GetMapping(value = "/excel", produces = XLSX)
    public ResponseEntity<byte[]> excel(Filter filter) {
        return download(service.excel(filter.startDate(), filter.endDate(), filter.company(), filter.division(), filter.area(), filter.agreementType(),
                filter.visitStatus(), filter.search(), filter.pageNumber(), filter.pageSize()),
                MediaType.parseMediaType(XLSX), "service-visit-report.xlsx");
    }

    private ResponseEntity<byte[]> download(byte[] data, MediaType type, String filename) {
        return ResponseEntity.ok().contentType(type).contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(filename).build().toString()).body(data);
    }

    public record Filter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) Division division,
            @RequestParam(required = false) Area area,
            @RequestParam(required = false) AgreementType agreementType,
            @RequestParam(required = false) ServiceScheduleStatus visitStatus,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        int pageNumber() { return page == null ? 0 : page; }
        int pageSize() { return size == null ? 50 : size; }
    }
}
