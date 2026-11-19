package com.gestetner.servvista.Controllers;

import com.gestetner.servvista.Service.InitialMachineImportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/initial-machine-import")
@PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
public class InitialMachineImportController {
    private final InitialMachineImportService service;

    public InitialMachineImportController(InitialMachineImportService service) { this.service = service; }

    @GetMapping(value = "/template", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> template() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=initial-machine-import-template.xlsx")
                .body(service.template());
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public InitialMachineImportService.ImportResult importFile(
            @RequestPart("file") MultipartFile file, @RequestParam Long performedBy) {
        return service.importFile(file, performedBy);
    }
}
