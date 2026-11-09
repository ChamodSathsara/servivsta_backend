package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.reports.ServiceVisitReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Models.Enums.Services.ServiceScheduleStatus;
import com.gestetner.servvista.Repositories.services.ServiceScheduleRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServiceVisitReportService {
    private static final String[] HEADERS = {
            "Machine ref", "Serial", "Company", "Division", "Customer", "Site", "Area",
            "Agreement", "Type", "Visit", "Status", "Tech code", "Technician", "Expected date"
    };
    private final ServiceScheduleRepository repository;

    public ServiceVisitReportService(ServiceScheduleRepository repository) {
        this.repository = repository;
    }

    public Page<ServiceVisitReportRow> report(
            Company company, Division division, Area area, AgreementType agreementType,
            ServiceScheduleStatus visitStatus, String search, int page, int size) {
        validate(page, size);
        repository.markScheduledVisitsDue(LocalDate.now());
        String normalized = search == null || search.isBlank() ? null : search.trim();
        return repository.findServiceVisitReport(company, division, area, agreementType,
                visitStatus, normalized, PageRequest.of(page, size,
                        Sort.by(Sort.Direction.DESC, "expectedVisitDate")
                                .and(Sort.by(Sort.Direction.DESC, "serviceScheduleId"))));
    }

    public byte[] pdf(Company company, Division division, Area area, AgreementType agreementType,
                      ServiceScheduleStatus status, String search, int page, int size) {
        List<ServiceVisitReportRow> rows = report(company, division, area, agreementType,
                status, search, page, size).getContent();
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A3.rotate(), 20, 20, 20, 20);
            PdfWriter.getInstance(document, output);
            document.open();
            Paragraph title = new Paragraph("Service Visit Report",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Rows: " + rows.size()));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(HEADERS.length);
            table.setWidthPercentage(100);
            table.setHeaderRows(1);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, Color.WHITE);
            for (String header : HEADERS) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new Color(22, 96, 86));
                cell.setPadding(4);
                table.addCell(cell);
            }
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 6);
            for (ServiceVisitReportRow row : rows) {
                for (String value : values(row)) {
                    PdfPCell cell = new PdfPCell(new Phrase(value, bodyFont));
                    cell.setPadding(3);
                    table.addCell(cell);
                }
            }
            document.add(table);
            document.close();
            return output.toByteArray();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to generate service visit PDF report", exception);
        }
    }

    public byte[] excel(Company company, Division division, Area area, AgreementType agreementType,
                        ServiceScheduleStatus status, String search, int page, int size) {
        List<ServiceVisitReportRow> rows = report(company, division, area, agreementType,
                status, search, page, size).getContent();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Service Visits");
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            style.setFont(font);
            Row header = sheet.createRow(0);
            for (int index = 0; index < HEADERS.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(HEADERS[index]);
                cell.setCellStyle(style);
            }
            int rowIndex = 1;
            for (ServiceVisitReportRow reportRow : rows) {
                Row row = sheet.createRow(rowIndex++);
                String[] values = values(reportRow);
                for (int index = 0; index < values.length; index++) row.createCell(index).setCellValue(values[index]);
            }
            for (int index = 0; index < HEADERS.length; index++) {
                sheet.autoSizeColumn(index);
                sheet.setColumnWidth(index, Math.min(sheet.getColumnWidth(index) + 512, 12000));
            }
            workbook.write(output);
            return output.toByteArray();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to generate service visit Excel report", exception);
        }
    }

    private void validate(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page must be zero or greater");
        if (size < 1 || size > 5000) throw new IllegalArgumentException("size must be between 1 and 5000");
    }

    private String[] values(ServiceVisitReportRow row) {
        return new String[]{text(row.machineReferenceNumber()), text(row.serialNumber()),
                text(row.company()), text(row.division()), text(row.customerName()), text(row.siteName()),
                text(row.area()), text(row.agreementNumber()), text(row.agreementType()),
                row.agreementYearNumber() + "/" + row.visitNumber(), text(row.visitStatus()),
                text(row.technicianCode()), text(row.technicianName()), text(row.expectedVisitDate())};
    }

    private String text(Object value) { return value == null ? "" : value.toString(); }
}
