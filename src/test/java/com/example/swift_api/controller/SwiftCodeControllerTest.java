package com.example.swift_api.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.swift_api.exception.SwiftCodeException;
import com.example.swift_api.model.SwiftCode;
import com.example.swift_api.service.ExcelImportService;
import com.example.swift_api.service.SwiftCodeService;

@WebMvcTest(SwiftCodeController.class)
public class SwiftCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SwiftCodeService swiftCodeService;

    @MockBean
    private ExcelImportService excelImportService;

    @Test
    void shouldGetSwiftCodesByCountry() throws Exception {
        // given
        String countryCode = "PL";
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setSwiftCode("PKOPPLPWXXX");
        swiftCode.setCountryISO2(countryCode);
        swiftCode.setName("PKO Bank Polski");
        
        when(swiftCodeService.getByCountry(countryCode))
            .thenReturn(Arrays.asList(swiftCode));

        // when & then
        mockMvc.perform(get("/v1/swift-codes/country/" + countryCode)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].swiftCode").value("PKOPPLPWXXX"))
                .andExpect(jsonPath("$[0].countryISO2").value(countryCode));
    }
    @Test
void shouldGetSwiftCodeByCode() throws Exception {
    // given
    String swiftCode = "PKOPPLPWXXX";
    SwiftCode expectedCode = new SwiftCode();
    expectedCode.setSwiftCode(swiftCode);
    expectedCode.setName("PKO Bank Polski");
    expectedCode.setCountryISO2("PL");
    
    when(swiftCodeService.getBySwiftCode(swiftCode)).thenReturn(expectedCode);

    // when & then
    mockMvc.perform(get("/v1/swift-codes/" + swiftCode)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.swiftCode").value(swiftCode))
            .andExpect(jsonPath("$.name").value("PKO Bank Polski"))
            .andExpect(jsonPath("$.countryISO2").value("PL"));
}

@Test
void shouldReturn404WhenSwiftCodeNotFound() throws Exception {
    // given
    String nonExistingCode = "NONEXISTING";
    when(swiftCodeService.getBySwiftCode(nonExistingCode))
            .thenThrow(new SwiftCodeException("Swift code not found"));

    // when & then
    mockMvc.perform(get("/v1/swift-codes/" + nonExistingCode)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())  // zmiana na badRequest bo tak mamy w GlobalExceptionHandler
            .andExpect(jsonPath("$.message").value("Swift code not found"));
}
}