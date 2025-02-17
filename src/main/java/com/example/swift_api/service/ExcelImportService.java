package com.example.swift_api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.swift_api.model.SwiftCode;
import com.example.swift_api.repository.SwiftCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelImportService {
    private final SwiftCodeRepository swiftCodeRepository;

    @Transactional
    public void importExcelFile(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, SwiftCode> swiftCodes = new HashMap<>();
            
            // Wczytaj wszystkie kody SWIFT
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                SwiftCode swiftCode = new SwiftCode();
                swiftCode.setCountryISO2(getStringCellValue(row.getCell(0)));
                swiftCode.setSwiftCode(getStringCellValue(row.getCell(1)));
                swiftCode.setCodeType(getStringCellValue(row.getCell(2)));
                swiftCode.setName(getStringCellValue(row.getCell(3)));
                swiftCode.setAddress(getStringCellValue(row.getCell(4)));
                swiftCode.setTownName(getStringCellValue(row.getCell(5)));
                swiftCode.setCountryName(getStringCellValue(row.getCell(6)));
                
                String swiftCodeStr = swiftCode.getSwiftCode();
                swiftCode.setHeadquarter(swiftCodeStr.endsWith("XXX"));
                
                swiftCodes.put(swiftCodeStr, swiftCode);
            }
            
            // Ustaw powiązania
            swiftCodes.values().stream()
                .filter(code -> !code.isHeadquarter())
                .forEach(code -> {
                    String potentialHeadquarter = code.getSwiftCode().substring(0, 8) + "XXX";
                    SwiftCode headquarter = swiftCodes.get(potentialHeadquarter);
                    if (headquarter != null) {
                        code.setHeadquarterBank(headquarter);
                    }
                });
            
            // Zapisz wszystko jednym wywołaniem
            swiftCodeRepository.saveAll(new ArrayList<>(swiftCodes.values()));
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }
}