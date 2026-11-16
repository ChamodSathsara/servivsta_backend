package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.sales.NewSaleWorkflowDtos.*;
import com.gestetner.servvista.Models.Enums.Machines.*;
import com.gestetner.servvista.Models.Enums.Organization.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class NewSaleExcelService {
    private static final String[] SALES={"serialNumber","company","division","modelId","machineInvoiceId","dealerId","representativeId","creditNoteNumber","machineNote","statusReason","customerId","warrantyType","warrantyStart","warrantyEnd","warrantyNote"};
    private static final String[] ACCESSORIES={"machineSerialNumber","accessoryName","accessorySerialNumber","quantity","note"};
    private final NewSaleWorkflowService workflows;
    private final MachineModelService models; private final MachineInvoiceService invoices;
    private final CustomerService customers; private final DealerService dealers; private final RepService reps;
    public NewSaleExcelService(NewSaleWorkflowService workflows,MachineModelService models,MachineInvoiceService invoices,CustomerService customers,DealerService dealers,RepService reps){this.workflows=workflows;this.models=models;this.invoices=invoices;this.customers=customers;this.dealers=dealers;this.reps=reps;}

    public byte[] template(){try(var book=new XSSFWorkbook();var out=new ByteArrayOutputStream()){
        var sales=book.createSheet("Sales");header(sales,SALES);var sr=sales.createRow(1);String[] sv={"SERIAL-001","GESTETNER","OUTRIGHT","18","1","","","","","Machine registered through new sale","1","MACHINE",LocalDate.now().toString(),LocalDate.now().plusYears(1).toString(),""};for(int i=0;i<sv.length;i++)sr.createCell(i).setCellValue(sv[i]);
        var accessories=book.createSheet("Accessories");header(accessories,ACCESSORIES);var ar=accessories.createRow(1);String[] av={"SERIAL-001","Power cable","PC-001","1","Packed with machine"};for(int i=0;i<av.length;i++)ar.createCell(i).setCellValue(av[i]);
        var refs=book.createSheet("Reference Data");int r=0;r=reference(refs,r,"MODELS: ID | NUMBER | NAME",models.getAll().stream().map(x->List.of(String.valueOf(x.modelId()),x.modelNumber(),x.modelName())).toList());r=reference(refs,r,"INVOICES: ID | NUMBER",invoices.getAll().stream().map(x->List.of(String.valueOf(x.machineInvoiceId()),x.invoiceNumber())).toList());r=reference(refs,r,"CUSTOMERS: ID | NAME",customers.getAll().stream().map(x->List.of(String.valueOf(x.customerId()),x.customerName())).toList());r=reference(refs,r,"DEALERS: ID | NAME",dealers.getAll().stream().map(x->List.of(String.valueOf(x.dealerId()),x.dealerName())).toList());reference(refs,r,"REPRESENTATIVES: ID | NAME",reps.getAll().stream().map(x->List.of(String.valueOf(x.repId()),x.repName())).toList());
        for(var s:List.of(sales,accessories,refs))for(int i=0;i<Math.min(s.getRow(0).getLastCellNum(),15);i++)s.autoSizeColumn(i);book.write(out);return out.toByteArray();
    }catch(IOException e){throw new IllegalStateException("Could not create Excel template",e);}}

    public ImportResult importFile(MultipartFile file,Long performedBy){if(file==null||file.isEmpty())throw new IllegalArgumentException("Choose a non-empty .xlsx file");List<RowError> errors=new ArrayList<>();List<FinanceStageRequest> requests=new ArrayList<>();try(var book=new XSSFWorkbook(file.getInputStream())){var sales=sheet(book,"Sales");var accessories=sheet(book,"Accessories");headers(sales,SALES);headers(accessories,ACCESSORIES);var accessoryMap=parseAccessories(accessories,errors);var f=new DataFormatter();Set<String> serials=new HashSet<>();for(int i=1;i<=sales.getLastRowNum();i++){var row=sales.getRow(i);if(blank(row,f))continue;try{String serial=text(row,0,f,true).toUpperCase();if(!serials.add(serial))throw new IllegalArgumentException("Duplicate serialNumber");LocalDate start=date(row,12,f),end=date(row,13,f);if(end.isBefore(start))throw new IllegalArgumentException("warrantyEnd cannot be before warrantyStart");requests.add(new FinanceStageRequest(serial,Company.valueOf(text(row,1,f,true).toUpperCase()),id(row,3,f,true),id(row,4,f,true),id(row,5,f,false),id(row,6,f,false),text(row,7,f,false),text(row,8,f,false),text(row,9,f,false),id(row,10,f,true),WarrantyType.valueOf(text(row,11,f,true).toUpperCase()),start,end,(int)Math.max(1,ChronoUnit.MONTHS.between(start,end)),text(row,14,f,false),Division.valueOf(text(row,2,f,true).toUpperCase()),accessoryMap.getOrDefault(serial,List.of()),performedBy));}catch(Exception e){errors.add(new RowError("Sales",i+1,message(e)));}}for(String serial:accessoryMap.keySet())if(!serials.contains(serial))errors.add(new RowError("Accessories",0,"No Sales row for "+serial));}catch(IOException e){throw new IllegalArgumentException("Excel file could not be read",e);}if(!errors.isEmpty())return new ImportResult(0,requests.size(),errors,List.of());List<Long> ids=new ArrayList<>();for(int i=0;i<requests.size();i++)try{ids.add(workflows.createFinance(requests.get(i)).workflowId());}catch(Exception e){errors.add(new RowError("Sales",i+2,message(e)));}return new ImportResult(ids.size(),errors.size(),errors,ids);}

    private Map<String,List<AccessoryRequest>> parseAccessories(Sheet s,List<RowError> errors){Map<String,List<AccessoryRequest>> result=new LinkedHashMap<>();var f=new DataFormatter();for(int i=1;i<=s.getLastRowNum();i++){var row=s.getRow(i);if(blank(row,f))continue;try{String serial=text(row,0,f,true).toUpperCase();int qty=Integer.parseInt(text(row,3,f,true).replace(".0",""));if(qty<1)throw new IllegalArgumentException("quantity must be at least 1");result.computeIfAbsent(serial,k->new ArrayList<>()).add(new AccessoryRequest(UUID.randomUUID().toString(),text(row,1,f,true),text(row,2,f,false),qty,text(row,4,f,false)));}catch(Exception e){errors.add(new RowError("Accessories",i+1,message(e)));}}return result;}
    private void header(Sheet s,String[] values){var row=s.createRow(0);var style=s.getWorkbook().createCellStyle();var font=s.getWorkbook().createFont();font.setBold(true);style.setFont(font);for(int i=0;i<values.length;i++){var c=row.createCell(i);c.setCellValue(values[i]);c.setCellStyle(style);}s.createFreezePane(0,1);}
    private int reference(Sheet s,int start,String title,List<List<String>> rows){s.createRow(start).createCell(0).setCellValue(title);int n=start+1;for(var values:rows){var row=s.createRow(n++);for(int i=0;i<values.size();i++)row.createCell(i).setCellValue(values.get(i));}return n+2;}
    private Sheet sheet(Workbook b,String name){var s=b.getSheet(name);if(s==null)throw new IllegalArgumentException("Missing sheet: "+name);return s;}
    private void headers(Sheet s,String[] expected){var f=new DataFormatter();var row=s.getRow(0);if(row==null)throw new IllegalArgumentException(s.getSheetName()+" header row is missing");for(int i=0;i<expected.length;i++)if(!expected[i].equals(f.formatCellValue(row.getCell(i)).trim()))throw new IllegalArgumentException(s.getSheetName()+" column "+(i+1)+" must be "+expected[i]);}
    private boolean blank(Row r,DataFormatter f){if(r==null)return true;for(Cell c:r)if(!f.formatCellValue(c).trim().isEmpty())return false;return true;}
    private String text(Row r,int col,DataFormatter f,boolean required){String v=f.formatCellValue(r.getCell(col)).trim();if(required&&v.isEmpty())throw new IllegalArgumentException("Column "+(col+1)+" is required");return v.isEmpty()?null:v;}
    private Long id(Row r,int col,DataFormatter f,boolean required){String v=text(r,col,f,required);if(v==null)return null;try{return Long.valueOf(v.replace(".0",""));}catch(Exception e){throw new IllegalArgumentException("Column "+(col+1)+" must contain a valid ID");}}
    private LocalDate date(Row r,int col,DataFormatter f){var c=r.getCell(col);if(c!=null&&DateUtil.isCellDateFormatted(c))return c.getLocalDateTimeCellValue().toLocalDate();try{return LocalDate.parse(text(r,col,f,true));}catch(Exception e){throw new IllegalArgumentException("Column "+(col+1)+" date must use YYYY-MM-DD");}}
    private String message(Exception e){return e.getMessage()==null?e.getClass().getSimpleName():e.getMessage();}
    public record RowError(String sheet,int row,String message){} public record ImportResult(int imported,int failed,List<RowError> errors,List<Long> workflowIds){}
}
