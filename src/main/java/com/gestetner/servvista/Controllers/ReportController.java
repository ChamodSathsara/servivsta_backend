package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Dto.reports.MachineAgreementReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports/machine-agreements")
@Tag(name = "Reports")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
public class ReportController {

    private static final String XLSX =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/ns")
    @Operation(summary = "Get paginated NS machine agreements as JSON")
    public Page<MachineAgreementReportRow> ns(ReportFilter filter) {
        return json(AgreementType.NS, filter);
    }

    @GetMapping(value = "/ns/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Download NS machine agreements as PDF")
    public ResponseEntity<byte[]> nsPdf(ReportFilter filter) {
        return pdf(AgreementType.NS, filter);
    }

    @GetMapping(value = "/ns/excel", produces = XLSX)
    @Operation(summary = "Download NS machine agreements as Excel")
    public ResponseEntity<byte[]> nsExcel(ReportFilter filter) {
        return excel(AgreementType.NS, filter);
    }

    @GetMapping("/fs")
    @Operation(summary = "Get paginated FS machine agreements as JSON")
    public Page<MachineAgreementReportRow> fs(ReportFilter filter) {
        return json(AgreementType.FS, filter);
    }

    @GetMapping(value = "/fs/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Download FS machine agreements as PDF")
    public ResponseEntity<byte[]> fsPdf(ReportFilter filter) {
        return pdf(AgreementType.FS, filter);
    }

    @GetMapping(value = "/fs/excel", produces = XLSX)
    @Operation(summary = "Download FS machine agreements as Excel")
    public ResponseEntity<byte[]> fsExcel(ReportFilter filter) {
        return excel(AgreementType.FS, filter);
    }

    @GetMapping("/ma")
    @Operation(summary = "Get paginated MA machine agreements as JSON")
    public Page<MachineAgreementReportRow> ma(ReportFilter filter) {
        return json(AgreementType.MA, filter);
    }

    @GetMapping(value = "/ma/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Download MA machine agreements as PDF")
    public ResponseEntity<byte[]> maPdf(ReportFilter filter) {
        return pdf(AgreementType.MA, filter);
    }

    @GetMapping(value = "/ma/excel", produces = XLSX)
    @Operation(summary = "Download MA machine agreements as Excel")
    public ResponseEntity<byte[]> maExcel(ReportFilter filter) {
        return excel(AgreementType.MA, filter);
    }

    private Page<MachineAgreementReportRow> json(AgreementType type, ReportFilter filter) {
        return reportService.machineAgreements(type, filter.startDate(), filter.lastDate(),
                filter.area(), filter.mainTechnicianId(), filter.division(), filter.company(),
                filter.pageNumber(), filter.pageSize());
    }

    private ResponseEntity<byte[]> pdf(AgreementType type, ReportFilter filter) {
        byte[] report = reportService.pdf(type, filter.startDate(), filter.lastDate(),
                filter.area(), filter.mainTechnicianId(), filter.division(), filter.company(),
                filter.pageNumber(), filter.pageSize());
        return download(report, MediaType.APPLICATION_PDF,
                type.name().toLowerCase() + "-machine-agreements.pdf");
    }

    private ResponseEntity<byte[]> excel(AgreementType type, ReportFilter filter) {
        byte[] report = reportService.excel(type, filter.startDate(), filter.lastDate(),
                filter.area(), filter.mainTechnicianId(), filter.division(), filter.company(),
                filter.pageNumber(), filter.pageSize());
        return download(report, MediaType.parseMediaType(XLSX),
                type.name().toLowerCase() + "-machine-agreements.xlsx");
    }

    private ResponseEntity<byte[]> download(byte[] data, MediaType contentType, String filename) {
        return ResponseEntity.ok()
                .contentType(contentType)
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(filename).build().toString())
                .body(data);
    }

    public record ReportFilter(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate lastDate,
            @RequestParam(required = false) Area area,
            @RequestParam(required = false) Long mainTechnicianId,
            @RequestParam(required = false) Division division,
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        int pageNumber() {
            return page == null ? 0 : page;
        }

        int pageSize() {
            return size == null ? 50 : size;
        }
    }
}
