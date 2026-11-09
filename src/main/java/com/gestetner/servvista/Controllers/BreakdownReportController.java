package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.reports.BreakdownJobReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Service.BreakdownReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports/breakdown-jobs")
@Tag(name = "Reports")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
public class BreakdownReportController {

    private static final String XLSX =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final BreakdownReportService reportService;

    public BreakdownReportController(BreakdownReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @Operation(summary = "Get the paginated breakdown job report")
    public Page<BreakdownJobReportRow> report(BreakdownReportFilter filter) {
        return reportService.report(filter.startDate(), filter.endDate(), filter.company(), filter.division(), filter.area(),
                filter.agreementType(), filter.jobStatus(), filter.search(),
                filter.pageNumber(), filter.pageSize());
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Download the breakdown job report as PDF")
    public ResponseEntity<byte[]> pdf(BreakdownReportFilter filter) {
        return download(reportService.pdf(filter.startDate(), filter.endDate(), filter.company(), filter.division(), filter.area(),
                        filter.agreementType(), filter.jobStatus(), filter.search(),
                        filter.pageNumber(), filter.pageSize()),
                MediaType.APPLICATION_PDF, "breakdown-job-report.pdf");
    }

    @GetMapping(value = "/excel", produces = XLSX)
    @Operation(summary = "Download the breakdown job report as Excel")
    public ResponseEntity<byte[]> excel(BreakdownReportFilter filter) {
        return download(reportService.excel(filter.startDate(), filter.endDate(), filter.company(), filter.division(), filter.area(),
                        filter.agreementType(), filter.jobStatus(), filter.search(),
                        filter.pageNumber(), filter.pageSize()),
                MediaType.parseMediaType(XLSX), "breakdown-job-report.xlsx");
    }

    private ResponseEntity<byte[]> download(byte[] data, MediaType type, String filename) {
        return ResponseEntity.ok().contentType(type).contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(filename).build().toString())
                .body(data);
    }

    public record BreakdownReportFilter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) Division division,
            @RequestParam(required = false) Area area,
            @RequestParam(required = false) AgreementType agreementType,
            @RequestParam(required = false) BreakdownStatus jobStatus,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        int pageNumber() { return page == null ? 0 : page; }
        int pageSize() { return size == null ? 50 : size; }
    }
}
