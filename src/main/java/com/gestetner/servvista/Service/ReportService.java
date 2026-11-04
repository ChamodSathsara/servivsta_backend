package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.reports.MachineAgreementReportRow;
import com.gestetner.servvista.Models.Enums.Agreements.AgreementType;
import com.gestetner.servvista.Models.Enums.Identity.TechnicianRole;
import com.gestetner.servvista.Models.Enums.Organization.Area;
import com.gestetner.servvista.Repositories.agreements.MachineAgreementRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private static final String[] HEADERS = {
            "Agreement", "Status", "Start date", "End date", "Machine ref",
            "Serial number", "Model", "Customer", "Site", "Area",
            "Main technician", "Team"
    };

    private final MachineAgreementRepository agreementRepository;

    public ReportService(MachineAgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    public Page<MachineAgreementReportRow> machineAgreements(
            AgreementType agreementType,
            LocalDate startDate,
            LocalDate lastDate,
            Area area,
            Long mainTechnicianId,
            TechnicianRole team,
            int page,
            int size) {
        validate(startDate, lastDate, page, size);
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "agreementStartDate")
                        .and(Sort.by(Sort.Direction.ASC, "agreementId")));
        return agreementRepository.findReport(agreementType, startDate, lastDate,
                area, mainTechnicianId, team, pageable);
    }

    public byte[] pdf(
            AgreementType type,
            LocalDate startDate,
            LocalDate lastDate,
            Area area,
            Long mainTechnicianId,
            TechnicianRole team,
            int page,
            int size) {
        List<MachineAgreementReportRow> rows = machineAgreements(type, startDate,
                lastDate, area, mainTechnicianId, team, page, size).getContent();
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate(), 24, 24, 24, 24);
            PdfWriter.getInstance(document, output);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph(type + " Machine Agreement Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Period: " + startDate + " to " + lastDate));
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
            for (MachineAgreementReportRow row : rows) {
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
            throw new IllegalStateException("Unable to generate PDF report", exception);
        }
    }

    public byte[] excel(
            AgreementType type,
            LocalDate startDate,
            LocalDate lastDate,
            Area area,
            Long mainTechnicianId,
            TechnicianRole team,
            int page,
            int size) {
        List<MachineAgreementReportRow> rows = machineAgreements(type, startDate,
                lastDate, area, mainTechnicianId, team, page, size).getContent();
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(type + " Agreements");
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
            for (MachineAgreementReportRow reportRow : rows) {
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
            throw new IllegalStateException("Unable to generate Excel report", exception);
        }
    }

    private void validate(LocalDate startDate, LocalDate lastDate, int page, int size) {
        if (startDate == null || lastDate == null) {
            throw new IllegalArgumentException("startDate and lastDate are required");
        }
        if (lastDate.isBefore(startDate)) {
            throw new IllegalArgumentException("lastDate must be on or after startDate");
        }
        if (page < 0) {
            throw new IllegalArgumentException("page must be zero or greater");
        }
        if (size < 1 || size > 5000) {
            throw new IllegalArgumentException("size must be between 1 and 5000");
        }
    }

    private String[] values(MachineAgreementReportRow row) {
        return new String[]{
                text(row.agreementNumber()), text(row.agreementStatus()),
                text(row.agreementStartDate()), text(row.agreementEndDate()),
                text(row.machineReferenceNumber()), text(row.serialNumber()),
                text(row.modelNumber()) + " - " + text(row.modelName()),
                text(row.customerName()), text(row.siteName()), text(row.area()),
                text(row.mainTechnicianName()), text(row.team())
        };
    }

    private String text(Object value) {
        return value == null ? "" : value.toString();
    }
}
