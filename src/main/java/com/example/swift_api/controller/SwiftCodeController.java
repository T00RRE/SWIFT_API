package com.example.swift_api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.swift_api.model.SwiftCode;
import com.example.swift_api.service.ExcelImportService;
import com.example.swift_api.service.SwiftCodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/swift-codes")
@RequiredArgsConstructor
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;
    private final ExcelImportService excelImportService;

    @GetMapping("/{swiftCode}")
    public ResponseEntity<SwiftCode> getSwiftCode(@PathVariable String swiftCode) {
        return ResponseEntity.ok(swiftCodeService.getBySwiftCode(swiftCode));
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<List<SwiftCode>> getByCountry(@PathVariable String countryISO2code) {
        return ResponseEntity.ok(swiftCodeService.getByCountry(countryISO2code));
    }

    @PostMapping
    public ResponseEntity<SwiftCode> createSwiftCode(@RequestBody SwiftCode swiftCode) {
        return ResponseEntity.ok(swiftCodeService.save(swiftCode));
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<Void> deleteSwiftCode(@PathVariable String swiftCode) {
        swiftCodeService.deleteBySwiftCode(swiftCode);
        return ResponseEntity.ok().build();
    }
@PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@Operation(summary = "Import SWIFT codes from Excel file")
@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
public ResponseEntity<String> importExcelFile(
    @Parameter(description = "Excel file to import", required = true)
    @RequestParam("file") MultipartFile file) {
    try {
        excelImportService.importExcelFile(file);
        return ResponseEntity.ok("Import successful");
    } catch (IOException e) {
        return ResponseEntity.badRequest().body("Error during import: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Unexpected error: " + e.getMessage());
    }
}
}