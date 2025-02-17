package com.example.swift_api.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.swift_api.model.SwiftCode;
import com.example.swift_api.repository.SwiftCodeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SwiftCodeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @AfterEach
    void cleanup() {
        swiftCodeRepository.deleteAll();
    }

    @Test
    void shouldSaveAndRetrieveSwiftCode() throws Exception {
        // given
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setSwiftCode("PKOPPLPWXXX");
        swiftCode.setName("PKO Bank Polski");
        swiftCode.setCountryISO2("PL");
        swiftCode.setCountryName("POLAND");
        swiftCode.setHeadquarter(true);
        swiftCodeRepository.save(swiftCode);

        // when & then
        mockMvc.perform(get("/v1/swift-codes/PKOPPLPWXXX")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("PKOPPLPWXXX"))
                .andExpect(jsonPath("$.name").value("PKO Bank Polski"))
                .andExpect(jsonPath("$.countryISO2").value("PL"));
    }

    @Test
    void shouldRetrieveSwiftCodesByCountry() throws Exception {
        // given
        SwiftCode swiftCode1 = new SwiftCode();
        swiftCode1.setSwiftCode("PKOPPLPWXXX");
        swiftCode1.setName("PKO Bank Polski");
        swiftCode1.setCountryISO2("PL");
        swiftCode1.setCountryName("POLAND");
        swiftCode1.setHeadquarter(true);

        SwiftCode swiftCode2 = new SwiftCode();
        swiftCode2.setSwiftCode("BREXPLPWXXX");
        swiftCode2.setName("mBank");
        swiftCode2.setCountryISO2("PL");
        swiftCode2.setCountryName("POLAND");
        swiftCode2.setHeadquarter(true);

        swiftCodeRepository.save(swiftCode1);
        swiftCodeRepository.save(swiftCode2);

        // when & then
        mockMvc.perform(get("/v1/swift-codes/country/PL")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].countryISO2").value("PL"))
                .andExpect(jsonPath("$[1].countryISO2").value("PL"));
    }
}