package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.reports.BreakdownJobReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Breakdowns.BreakdownStatus;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.Enums.Organization.Division;
import com.gestetner.servvista.Repositories.breakdowns.BreakdownRepository;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.time.LocalDate;

@Service
public class BreakdownReportService {

    private static final String[] HEADERS = {
            "Breakdown", "Machine ref", "Serial", "Company", "Division", "Customer",
            "Site", "Area", "Agreement", "Job status", "Tech code", "Technician", "Created"
    };

    private final BreakdownRepository breakdownRepository;

    public BreakdownReportService(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    public Page<BreakdownJobReportRow> report(
            LocalDate startDate, LocalDate endDate,
            Company company, Division division, Area area, AgreementType agreementType,
            BreakdownStatus jobStatus, String search, int page, int size) {
        validate(startDate, endDate, page, size);
        String normalizedSearch = search == null || search.isBlank() ? null : search.trim();
        return breakdownRepository.findBreakdownReport(startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay(), company, division, area, agreementType,
                jobStatus, normalizedSearch, PageRequest.of(page, size,
                        Sort.by(Sort.Direction.DESC, "createdAt")
                                .and(Sort.by(Sort.Direction.DESC, "breakdownId"))));
    }

    public byte[] pdf(
            LocalDate startDate, LocalDate endDate,
            Company company, Division division, Area area, AgreementType agreementType,
            BreakdownStatus jobStatus, String search, int page, int size) {
        List<BreakdownJobReportRow> rows = report(startDate, endDate, company, division, area, agreementType,
                jobStatus, search, page, size).getContent();
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A3.rotate(), 20, 20, 20, 20);
            PdfWriter.getInstance(document, output);
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Breakdown Job Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Rows in this page: " + rows.size()));
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
            for (BreakdownJobReportRow row : rows) {
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
            throw new IllegalStateException("Unable to generate breakdown PDF report", exception);
        }
    }

    public byte[] excel(
            LocalDate startDate, LocalDate endDate,
            Company company, Division division, Area area, AgreementType agreementType,
            BreakdownStatus jobStatus, String search, int page, int size) {
        List<BreakdownJobReportRow> rows = report(startDate, endDate, company, division, area, agreementType,
                jobStatus, search, page, size).getContent();
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Breakdown Jobs");
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            Row header = sheet.createRow(0);
            for (int index = 0; index < HEADERS.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(HEADERS[index]);
                cell.setCellStyle(headerStyle);
            }
            int rowIndex = 1;
            for (BreakdownJobReportRow reportRow : rows) {
                Row excelRow = sheet.createRow(rowIndex++);
                String[] values = values(reportRow);
                for (int index = 0; index < values.length; index++) {
                    excelRow.createCell(index).setCellValue(values[index]);
                }
            }
            for (int index = 0; index < HEADERS.length; index++) {
                sheet.autoSizeColumn(index);
                sheet.setColumnWidth(index, Math.min(sheet.getColumnWidth(index) + 512, 12000));
            }
            workbook.write(output);
            return output.toByteArray();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to generate breakdown Excel report", exception);
        }
    }

    private void validate(LocalDate startDate, LocalDate endDate, int page, int size) {
        if (startDate == null || endDate == null) throw new IllegalArgumentException("startDate and endDate are required");
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("endDate must be on or after startDate");
        if (page < 0) throw new IllegalArgumentException("page must be zero or greater");
        if (size < 1 || size > 5000) {
            throw new IllegalArgumentException("size must be between 1 and 5000");
        }
    }

    private String[] values(BreakdownJobReportRow row) {
        return new String[]{
                text(row.breakdownNumber()), text(row.machineReferenceNumber()),
                text(row.serialNumber()), text(row.company()), text(row.division()),
                text(row.customerName()), text(row.siteName()), text(row.area()),
                text(row.agreementType()), text(row.jobStatus()), text(row.technicianCode()),
                text(row.technicianName()), text(row.createdAt())
        };
    }

    private String text(Object value) {
        return value == null ? "" : value.toString();
    }
}
