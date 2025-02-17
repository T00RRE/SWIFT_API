package com.example.swift_api.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.swift_api.repository.SwiftCodeRepository;

@ExtendWith(MockitoExtension.class)
public class ExcelImportServiceTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private ExcelImportService excelImportService;

    @Test
void shouldImportExcelFile() throws IOException {
    // given
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Swift Codes");
    
    // Create header row
    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("COUNTRY ISO2 CODE");
    headerRow.createCell(1).setCellValue("SWIFT CODE");
    headerRow.createCell(2).setCellValue("CODE TYPE");
    headerRow.createCell(3).setCellValue("NAME");
    headerRow.createCell(4).setCellValue("ADDRESS");
    headerRow.createCell(5).setCellValue("TOWN NAME");
    headerRow.createCell(6).setCellValue("COUNTRY NAME");

    // Create data row
    Row dataRow = sheet.createRow(1);
    dataRow.createCell(0).setCellValue("PL");
    dataRow.createCell(1).setCellValue("PKOPPLPWXXX");
    dataRow.createCell(2).setCellValue("BC11");
    dataRow.createCell(3).setCellValue("PKO Bank Polski");
    dataRow.createCell(4).setCellValue("ul. Puławska 15");
    dataRow.createCell(5).setCellValue("Warszawa");
    dataRow.createCell(6).setCellValue("POLAND");

    // Convert workbook to bytes
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    workbook.write(bos);
    byte[] bytes = bos.toByteArray();

    // Create mock MultipartFile
    MultipartFile file = new MockMultipartFile(
        "file",
        "test.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        bytes
    );

    // Configure mock
    when(swiftCodeRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

    // when
    excelImportService.importExcelFile(file);

    // then
    verify(swiftCodeRepository).saveAll(anyList());
}

    @Test
    void shouldHandleEmptyFile() {
        // given
        MultipartFile emptyFile = new MockMultipartFile(
            "file",
            "empty.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            new byte[0]
        );

        // when & then
        assertThrows(
            EmptyFileException.class,
            () -> excelImportService.importExcelFile(emptyFile)
        );
    }
}