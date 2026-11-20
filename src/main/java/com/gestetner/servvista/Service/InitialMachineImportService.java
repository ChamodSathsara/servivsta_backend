package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.agreements.MachineAgreementRequest;
import com.gestetner.servvista.Models.Enums.Agreements.*;
import com.gestetner.servvista.Models.Enums.Customers.*;
import com.gestetner.servvista.Models.Enums.Machines.*;
import com.gestetner.servvista.Models.Enums.Organization.*;
import com.gestetner.servvista.Models.entity.customers.*;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.machines.*;
import com.gestetner.servvista.Models.entity.organization.City;
import com.gestetner.servvista.Repositories.customers.*;
import com.gestetner.servvista.Repositories.identity.*;
import com.gestetner.servvista.Repositories.machines.*;
import com.gestetner.servvista.Repositories.organization.CityRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
@Transactional
public class InitialMachineImportService {
    private static final String SHEET = "Initial Machines";
    private static final String[] H = {
            "machineReferenceNumber","serialNumber","company","division",
            "modelNumber","modelName","machineTypeName",
            "customerSageCode","customerName","customerCompanies","customerGrade","customerType","customerSegment",
            "customerAddress1","customerAddress2","customerAddress3","customerTelephone","customerEmail",
            "siteName","siteAddress1","siteAddress2","siteAddress3","area","cityId","latitude","longitude","isHeadOffice",
            "contactName","contactMobile","contactEmail","contactDesignation",
            "invoiceNumber","belitaInvoiceNumber","invoiceDate","invoiceNote",
            "mainTechnicianCode","serviceTechnicianCode","originalInstallDate","assignmentType","machineNote","creditNoteNumber",
            "agreementType","agreementStartDate","agreementEndDate","agreementPeriodYears","visitsPerYear",
            "annualPayment","fullPayment","discount","vatPercentage","vatAmount","agreementNote"
    };

    private final MachineRepository machines; private final MachineModelRepository models;
    private final CustomerRepository customers; private final CompanyCustomerRepository customerCompanies;
    private final CustomerSiteRepository sites; private final SiteContactRepository contacts;
    private final MachineInvoiceRepository invoices; private final TechnicianRepository technicians;
    private final UserRepository users; private final CityRepository cities;
    private final ManufacturerRepository manufacturers; private final MachineTypeRepository machineTypes;
    private final MachineAssignmentRepository assignments; private final MachineTechnicianAssignmentRepository technicianAssignments;
    private final MachineStatusHistoryRepository statusHistory; private final MachineAgreementService agreements;
    private final ServiceScheduleService schedules;

    public InitialMachineImportService(MachineRepository machines, MachineModelRepository models,
            CustomerRepository customers, CompanyCustomerRepository customerCompanies,
            CustomerSiteRepository sites, SiteContactRepository contacts, MachineInvoiceRepository invoices,
            TechnicianRepository technicians, UserRepository users, CityRepository cities,
            ManufacturerRepository manufacturers, MachineTypeRepository machineTypes,
            MachineAssignmentRepository assignments, MachineTechnicianAssignmentRepository technicianAssignments,
            MachineStatusHistoryRepository statusHistory, MachineAgreementService agreements,
            ServiceScheduleService schedules) {
        this.machines=machines;this.models=models;this.customers=customers;this.customerCompanies=customerCompanies;
        this.sites=sites;this.contacts=contacts;this.invoices=invoices;this.technicians=technicians;this.users=users;
        this.cities=cities;this.manufacturers=manufacturers;this.machineTypes=machineTypes;this.assignments=assignments;
        this.technicianAssignments=technicianAssignments;this.statusHistory=statusHistory;this.agreements=agreements;this.schedules=schedules;
    }

    @Transactional(readOnly=true)
    public byte[] template() {
        try (XSSFWorkbook book=new XSSFWorkbook(); ByteArrayOutputStream out=new ByteArrayOutputStream()) {
            CellStyle header=header(book); Sheet input=book.createSheet(SHEET); writeHeader(input,header,H);
            input.createFreezePane(4,1); input.setAutoFilter(new CellRangeAddress(0,0,0,H.length-1));
            for(int i=0;i<H.length;i++) input.setColumnWidth(i,Math.min(28,Math.max(15,H[i].length()+2))*256);
            Sheet instructions=book.createSheet("Instructions"); String[] notes={
                    "One row contains the full model, customer, site, contact, invoice, machine and agreement dataset.",
                    "No model/customer/site/invoice database IDs are required. Existing records are reused by natural keys.",
                    "Enter either modelNumber or modelName. The model must already exist in Machine Models.",
                    "machineTypeName is required. A missing type is created once using its unique name.",
                    "customerCompanies accepts comma-separated values, e.g. GESTETNER,FINTECH.",
                    "siteAddress1 and customerSegment are optional. Blank customerSegment defaults to OTHERS.",
                    "Blank area defaults to UNKNOWN. Blank cityId uses the UNKNOWN city.",
                    "Contact and invoice fields may be empty. Dates must use YYYY-MM-DD.",
                    "FS/MA generate service visits; past dates become COMPLETED with note: system automatically change for complete.",
                    "NS does not generate service visits. All rows import in one transaction; any error rolls back the whole file."
            }; for(int i=0;i<notes.length;i++) instructions.createRow(i).createCell(0).setCellValue(notes[i]); instructions.setColumnWidth(0,120*256);
            references(book,header); book.write(out); return out.toByteArray();
        } catch(IOException e){throw new IllegalStateException("Could not create initial machine template",e);}
    }

    public ImportResult importFile(MultipartFile file,Long performedBy){
        if(file==null||file.isEmpty())throw new IllegalArgumentException("Choose a non-empty .xlsx file");
        if(!users.existsById(performedBy))throw new IllegalArgumentException("Importing user was not found");
        List<R> rows=parse(file);if(rows.isEmpty())throw new IllegalArgumentException("Initial Machines sheet has no data rows");
        Set<String> qs=new HashSet<>(),serials=new HashSet<>();for(R r:rows){if(!qs.add(r.q))throw r.error("Duplicate Q number in file");if(!serials.add(r.serial))throw r.error("Duplicate serial number in file");prevalidate(r);}
        int visits=0,completed=0;List<Long> ids=new ArrayList<>();
        for(R r:rows)try{Counts c=save(r,performedBy);ids.add(c.machineId);visits+=c.visits;completed+=c.completed;}catch(RuntimeException e){throw r.error(message(e));}
        return new ImportResult(rows.size(),visits,completed,ids);
    }

    private void prevalidate(R r){
        if(machines.existsByMachineReferenceNumberIgnoreCase(r.q))throw r.error("Q number already exists: "+r.q);
        if(machines.existsBySerialNumberIgnoreCase(r.serial))throw r.error("Serial number already exists: "+r.serial);
        if(findModel(r)==null)throw r.error("Existing model not found for modelNumber/modelName");
        if(r.cityId!=null&&!cities.existsById(r.cityId))throw r.error("City not found: "+r.cityId);
        technicians.findByTechCodeIgnoreCase(r.mainTech).orElseThrow(()->r.error("Main technician not found: "+r.mainTech));
        technicians.findByTechCodeIgnoreCase(r.serviceTech).orElseThrow(()->r.error("Service technician not found: "+r.serviceTech));
        if(!r.agreementEnd.isAfter(r.agreementStart))throw r.error("agreementEndDate must be after agreementStartDate");
        if(r.periodYears<1)throw r.error("agreementPeriodYears must be at least 1");
        if(r.agreementType!=AgreementType.NS&&r.visitsPerYear<1)throw r.error("visitsPerYear must be at least 1 for FS/MA");
    }

    private Counts save(R r,Long by){
        LocalDateTime now=LocalDateTime.now();
        MachineType machineType=findOrCreateMachineType(r.machineTypeName);MachineModel model=findModel(r);
        if(model.getMachineType()==null||!model.getMachineType().getMachineTypeId().equals(machineType.getMachineTypeId())){model.setMachineType(machineType);model=models.saveAndFlush(model);}
        Customer customer=findOrCreateCustomer(r,by,now);
        CustomerSite site=findOrCreateSite(r,customer,by,now);findOrCreateContact(r,site,by,now);
        MachineInvoice invoice=findOrCreateInvoice(r,customer,by,now);
        Technician main=technicians.findByTechCodeIgnoreCase(r.mainTech).orElseThrow();Technician service=technicians.findByTechCodeIgnoreCase(r.serviceTech).orElseThrow();
        LocalDate today=LocalDate.now();MachineStatus machineStatus=r.agreementEnd.isBefore(today)?MachineStatus.NO_SERVICE:r.agreementType==AgreementType.FS?MachineStatus.ACTIVE_FS:r.agreementType==AgreementType.MA?MachineStatus.ACTIVE_MA:MachineStatus.NO_SERVICE;
        Machine m=new Machine();m.setMachineReferenceNumber(r.q);m.setSerialNumber(r.serial);m.setCompany(r.company);m.setDivision(r.division);m.setModelId(model.getModelId());m.setCurrentStatus(machineStatus);m.setCurrentCustomerSiteId(site.getCustomerSiteId());m.setCurrentMainTechnicianId(main.getTechnicianId());m.setCurrentServiceTechnicianId(service.getTechnicianId());m.setMachineInvoiceId(invoice==null?null:invoice.getMachineInvoiceId());m.setOriginalInstallDate(r.installDate);m.setNote(r.machineNote);m.setCreditNoteNumber(r.creditNote);m.setCreatedBy(by);m.setCreatedAt(now);m=machines.saveAndFlush(m);
        var agreement=agreements.create(new MachineAgreementRequest(m.getMachineId(),r.agreementType,r.agreementStart,r.agreementEnd,r.periodYears,Math.max(1,r.visitsPerYear),r.annual,r.full,r.discount,r.vatPct,r.vatAmount,r.agreementEnd.isBefore(today)?AgreementStatus.EXPIRED:AgreementStatus.ACTIVE,!r.agreementEnd.isBefore(today),null,null,r.agreementNote,by,"Initial existing machine import"));
        MachineAssignment ma=new MachineAssignment();ma.setMachineId(m.getMachineId());ma.setCustomerId(customer.getCustomerId());ma.setCustomerSiteId(site.getCustomerSiteId());ma.setAssignmentType(r.assignmentType);ma.setAgreementId(agreement.agreementId());ma.setAssignedFrom(r.installDate);ma.setIsCurrent(true);ma.setCreatedBy(by);ma.setCreatedAt(now);assignments.saveAndFlush(ma);
        technicianAssignment(m.getMachineId(),main.getTechnicianId(),TechnicianAssignmentRole.MAIN,r.installDate,by,now);technicianAssignment(m.getMachineId(),service.getTechnicianId(),TechnicianAssignmentRole.SERVICE,r.installDate,by,now);
        MachineStatusHistory h=new MachineStatusHistory();h.setMachineId(m.getMachineId());h.setPreviousStatus(machineStatus);h.setNewStatus(machineStatus);h.setChangedAt(now);h.setChangedBy(by);h.setReason("Initial existing machine import");statusHistory.saveAndFlush(h);
        var generated=schedules.generateForImportedAgreement(agreement.agreementId(),m.getMachineId(),service.getTechnicianId(),now);return new Counts(m.getMachineId(),generated.generated(),generated.autoCompleted());
    }

    private MachineModel findModel(R r){List<MachineModel> found=new ArrayList<>();if(r.modelNumber!=null)found.addAll(models.findAllByModelNumberIgnoreCase(r.modelNumber));if(r.modelName!=null)found.addAll(models.findAllByModelNameIgnoreCase(r.modelName));return found.stream().filter(m->supports(m,r.company)).findFirst().orElse(null);}
    private MachineType findOrCreateMachineType(String name){return machineTypes.findFirstByMachineTypeNameIgnoreCase(name).orElseGet(()->{MachineType t=new MachineType();t.setMachineTypeName(name.trim());t.setMachineTypeDescription(null);t.setIsActive(true);return machineTypes.saveAndFlush(t);});}
    private boolean supports(MachineModel m,Company c){return m.getCompanies()==null||m.getCompanies().isEmpty()?m.getCompany()==c:m.getCompanies().contains(c);}
    private Customer findOrCreateCustomer(R r,Long by,LocalDateTime now){
        Customer c=customers.findBySageCodeIgnoreCase(r.sage).orElse(null);if(c==null){c=new Customer();c.setSageCode(r.sage);c.setCustomerName(r.customerName);c.setAddressLine1(r.customerAddress1);c.setAddressLine2(r.customerAddress2);c.setAddressLine3(r.customerAddress3);c.setHeadOfficeTelNumber(r.customerTel);c.setHeadOfficeEmail(r.customerEmail);c.setIsActive(true);c.setCustomerGrade(r.grade);c.setCustomerType(r.customerType);c.setCustomerSegment(r.segment);c.setCreatedAt(now);c.setCreatedBy(by);c=customers.saveAndFlush(c);}for(Company company:r.customerCompanies)if(!customerCompanies.existsByCustomerIdAndCompany(c.getCustomerId(),company)){CompanyCustomer cc=new CompanyCustomer();cc.setCustomerId(c.getCustomerId());cc.setCompany(company);customerCompanies.save(cc);}customerCompanies.flush();return c;
    }
    private CustomerSite findOrCreateSite(R r,Customer customer,Long by,LocalDateTime now){return sites.findByCustomerIdAndSiteNameIgnoreCase(customer.getCustomerId(),r.siteName).orElseGet(()->{City city=r.cityId==null?unknownCity():cities.findById(r.cityId).orElseThrow();CustomerSite s=new CustomerSite();s.setCustomerId(customer.getCustomerId());s.setSiteName(r.siteName);s.setAddressLine1(r.siteAddress1);s.setAddressLine2(r.siteAddress2);s.setAddressLine3(r.siteAddress3);s.setArea(r.area==null?Area.UNKNOWN:r.area);s.setCityId(city.getCityId());s.setLatitude(r.latitude);s.setLongitude(r.longitude);s.setIsHeadOffice(r.headOffice);s.setIsActive(true);s.setCreatedAt(now);s.setCreatedBy(by);return sites.saveAndFlush(s);});}
    private City unknownCity(){return cities.findFirstByAreaAndCityNameIgnoreCase(Area.UNKNOWN,"UNKNOWN").orElseGet(()->{City city=new City();city.setArea(Area.UNKNOWN);city.setCityName("UNKNOWN");city.setIsActive(true);return cities.saveAndFlush(city);});}
    private void findOrCreateContact(R r,CustomerSite site,Long by,LocalDateTime now){if(r.contactEmail==null)return;if(contacts.existsByCustomerSiteIdAndEmailIgnoreCase(site.getCustomerSiteId(),r.contactEmail))return;SiteContact c=new SiteContact();c.setCustomerSiteId(site.getCustomerSiteId());c.setContactName(r.contactName);c.setMobileNumber(r.contactMobile);c.setEmail(r.contactEmail);c.setDesignation(r.contactDesignation);c.setIsPrimary(true);c.setIsActive(true);c.setCreatedAt(now);c.setCreatedBy(by);contacts.saveAndFlush(c);}
    private MachineInvoice findOrCreateInvoice(R r,Customer customer,Long by,LocalDateTime now){if(r.invoiceNumber==null)return null;MachineInvoice existing=invoices.findByInvoiceNumberIgnoreCase(r.invoiceNumber).orElse(null);if(existing!=null){if(!existing.getCustomerId().equals(customer.getCustomerId()))throw r.error("Invoice belongs to another customer");return existing;}MachineInvoice i=new MachineInvoice();i.setInvoiceNumber(r.invoiceNumber);i.setBelitaInvoiceNumber(r.belita);i.setInvoiceDate(r.invoiceDate);i.setCustomerId(customer.getCustomerId());i.setNote(r.invoiceNote);i.setCreatedBy(by);i.setCreatedAt(now);return invoices.saveAndFlush(i);}
    private void technicianAssignment(Long mid,Long tid,TechnicianAssignmentRole role,LocalDate from,Long by,LocalDateTime now){MachineTechnicianAssignment a=new MachineTechnicianAssignment();a.setMachineId(mid);a.setTechnicianId(tid);a.setAssignmentRole(role);a.setAssignedFrom(from);a.setIsCurrent(true);a.setAssignedBy(by);a.setCreatedAt(now);technicianAssignments.saveAndFlush(a);}

    private List<R> parse(MultipartFile file){List<R> out=new ArrayList<>();try(XSSFWorkbook book=new XSSFWorkbook(file.getInputStream())){Sheet s=book.getSheet(SHEET);if(s==null)throw new IllegalArgumentException("Missing sheet: "+SHEET);headers(s);DataFormatter f=new DataFormatter();for(int i=1;i<=s.getLastRowNum();i++){Row x=s.getRow(i);if(blank(x,f))continue;int n=i+1;try{out.add(row(n,x,f));}catch(RuntimeException e){throw new IllegalArgumentException("Row "+n+": "+message(e),e);}}}catch(IOException e){throw new IllegalArgumentException("Excel file could not be read",e);}return out;}
    private R row(int n,Row r,DataFormatter f){
        Company company=en(Company.class,t(r,2,f,true)); Division division=en(Division.class,t(r,3,f,true));
        String modelNumber=upperNullable(t(r,4,f,false)),modelName=t(r,5,f,false);if(modelNumber==null&&modelName==null)throw new IllegalArgumentException("modelNumber or modelName is required");
        LocalDate start=date(r,42,f,true),end=date(r,43,f,true);
        String segment=t(r,12,f,false),area=t(r,22,f,false);
        return new R(n,upper(t(r,0,f,true)),upper(t(r,1,f,true)),company,division,modelNumber,modelName,t(r,6,f,true),upper(t(r,7,f,true)),t(r,8,f,true),companies(t(r,9,f,false),company),en(CustomerGrade.class,t(r,10,f,true)),en(CustomerType.class,t(r,11,f,true)),segment==null?CustomerSegment.OTHERS:en(CustomerSegment.class,segment),t(r,13,f,false),t(r,14,f,false),t(r,15,f,false),t(r,16,f,false),t(r,17,f,false),t(r,18,f,true),t(r,19,f,false),t(r,20,f,false),t(r,21,f,false),area==null?Area.UNKNOWN:en(Area.class,area),lng(r,23,f,false),dec(r,24,f),dec(r,25,f),bool(r,26,f),t(r,27,f,false),t(r,28,f,false),t(r,29,f,false),t(r,30,f,false),t(r,31,f,false),t(r,32,f,false),date(r,33,f,false),t(r,34,f,false),upper(t(r,35,f,true)),upper(t(r,36,f,true)),date(r,37,f,true),en(MachineAssignmentType.class,t(r,38,f,true)),t(r,39,f,false),t(r,40,f,false),en(AgreementType.class,t(r,41,f,true)),start,end,integer(r,44,f,true),integer(r,45,f,true),money(r,46,f),money(r,47,f),money(r,48,f),money(r,49,f),money(r,50,f),t(r,51,f,false));
    }
    private void references(XSSFWorkbook b,CellStyle h){Sheet s=b.createSheet("Reference Data");String[] cols={"manufacturerId","manufacturerName","machineTypeId","machineTypeName","cityId","cityName","area","techCode","technicianName"};writeHeader(s,h,cols);var ms=manufacturers.findAll();var ts=machineTypes.findAll();var cs=cities.findAll();var techs=technicians.findAll();int max=Math.max(Math.max(ms.size(),ts.size()),Math.max(cs.size(),techs.size()));for(int i=0;i<max;i++){Row r=s.createRow(i+1);if(i<ms.size()){r.createCell(0).setCellValue(ms.get(i).getManufacturerId());r.createCell(1).setCellValue(ms.get(i).getManufacturerName());}if(i<ts.size()){r.createCell(2).setCellValue(ts.get(i).getMachineTypeId());r.createCell(3).setCellValue(ts.get(i).getMachineTypeName());}if(i<cs.size()){r.createCell(4).setCellValue(cs.get(i).getCityId());r.createCell(5).setCellValue(cs.get(i).getCityName());r.createCell(6).setCellValue(cs.get(i).getArea().name());}if(i<techs.size()){r.createCell(7).setCellValue(techs.get(i).getTechCode());r.createCell(8).setCellValue(techs.get(i).getTechnicianName());}}for(int i=0;i<cols.length;i++)s.setColumnWidth(i,22*256);s.createFreezePane(0,1);}
    private CellStyle header(Workbook b){CellStyle s=b.createCellStyle();Font f=b.createFont();f.setBold(true);f.setColor(IndexedColors.WHITE.getIndex());s.setFont(f);s.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());s.setFillPattern(FillPatternType.SOLID_FOREGROUND);return s;}
    private void writeHeader(Sheet s,CellStyle st,String[] v){Row r=s.createRow(0);for(int i=0;i<v.length;i++){Cell c=r.createCell(i);c.setCellValue(v[i]);c.setCellStyle(st);}}
    private void headers(Sheet s){DataFormatter f=new DataFormatter();Row r=s.getRow(0);if(r==null)throw new IllegalArgumentException("Header row missing");for(int i=0;i<H.length;i++)if(!H[i].equals(f.formatCellValue(r.getCell(i)).trim()))throw new IllegalArgumentException("Column "+(i+1)+" must be "+H[i]);}
    private boolean blank(Row r,DataFormatter f){if(r==null)return true;for(Cell c:r)if(!f.formatCellValue(c).trim().isEmpty())return false;return true;}
    private String t(Row r,int c,DataFormatter f,boolean req){String v=f.formatCellValue(r.getCell(c)).trim();if(req&&v.isEmpty())throw new IllegalArgumentException(H[c]+" is required");return v.isEmpty()?null:v;}
    private Long lng(Row r,int c,DataFormatter f,boolean req){String v=t(r,c,f,req);if(v==null)return null;try{return Long.valueOf(v.replace(".0",""));}catch(Exception e){throw new IllegalArgumentException(H[c]+" must be an ID");}}
    private Integer integer(Row r,int c,DataFormatter f,boolean req){String v=t(r,c,f,req);if(v==null)return null;try{return Integer.valueOf(v.replace(".0",""));}catch(Exception e){throw new IllegalArgumentException(H[c]+" must be an integer");}}
    private BigDecimal money(Row r,int c,DataFormatter f){String v=t(r,c,f,false);if(v==null)return BigDecimal.ZERO;try{return new BigDecimal(v.replace(",",""));}catch(Exception e){throw new IllegalArgumentException(H[c]+" must be numeric");}}
    private BigDecimal dec(Row r,int c,DataFormatter f){String v=t(r,c,f,false);return v==null?null:new BigDecimal(v);}
    private LocalDate date(Row r,int c,DataFormatter f,boolean req){Cell x=r.getCell(c);if(x!=null&&x.getCellType()==CellType.NUMERIC&&DateUtil.isCellDateFormatted(x))return x.getLocalDateTimeCellValue().toLocalDate();String v=t(r,c,f,req);if(v==null)return null;try{return LocalDate.parse(v);}catch(Exception e){throw new IllegalArgumentException(H[c]+" must use YYYY-MM-DD");}}
    private boolean bool(Row r,int c,DataFormatter f){String v=t(r,c,f,true);return v.equalsIgnoreCase("true")||v.equals("1")||v.equalsIgnoreCase("yes");}
    private <E extends Enum<E>> E en(Class<E> type,String v){try{return Enum.valueOf(type,upper(v));}catch(Exception e){throw new IllegalArgumentException("Invalid "+type.getSimpleName()+": "+v);}}
    private Set<Company> companies(String v,Company fallback){LinkedHashSet<Company> out=new LinkedHashSet<>();if(v==null)out.add(fallback);else for(String x:v.split("[,|]"))out.add(en(Company.class,x.trim()));return out;}
    private String upper(String v){return v.trim().toUpperCase(Locale.ROOT);}private String upperNullable(String v){return v==null?null:upper(v);}private String message(Throwable e){return e.getMessage()==null?e.getClass().getSimpleName():e.getMessage();}

    private record R(int row,String q,String serial,Company company,Division division,String modelNumber,String modelName,String machineTypeName,String sage,String customerName,Set<Company> customerCompanies,CustomerGrade grade,CustomerType customerType,CustomerSegment segment,String customerAddress1,String customerAddress2,String customerAddress3,String customerTel,String customerEmail,String siteName,String siteAddress1,String siteAddress2,String siteAddress3,Area area,Long cityId,BigDecimal latitude,BigDecimal longitude,boolean headOffice,String contactName,String contactMobile,String contactEmail,String contactDesignation,String invoiceNumber,String belita,LocalDate invoiceDate,String invoiceNote,String mainTech,String serviceTech,LocalDate installDate,MachineAssignmentType assignmentType,String machineNote,String creditNote,AgreementType agreementType,LocalDate agreementStart,LocalDate agreementEnd,Integer periodYears,Integer visitsPerYear,BigDecimal annual,BigDecimal full,BigDecimal discount,BigDecimal vatPct,BigDecimal vatAmount,String agreementNote){IllegalArgumentException error(String m){return new IllegalArgumentException("Row "+row+": "+m);}}
    private record Counts(Long machineId,int visits,int completed){}
    public record ImportResult(int importedMachines,int generatedVisits,int autoCompletedVisits,List<Long> machineIds){}
}
