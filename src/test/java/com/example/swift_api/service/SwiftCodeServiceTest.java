package com.example.swift_api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.swift_api.model.SwiftCode;
import com.example.swift_api.repository.SwiftCodeRepository;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeServiceTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private SwiftCodeService swiftCodeService;

    @Test
    void shouldGetSwiftCodeByCode() {
        // given
        String swiftCode = "PKOPPLPWXXX";
        SwiftCode expectedSwiftCode = new SwiftCode();
        expectedSwiftCode.setSwiftCode(swiftCode);
        when(swiftCodeRepository.findById(swiftCode)).thenReturn(Optional.of(expectedSwiftCode));

        // when
        SwiftCode result = swiftCodeService.getBySwiftCode(swiftCode);

        // then
        assertNotNull(result);
        assertEquals(swiftCode, result.getSwiftCode());
    }

    @Test
    void shouldGetSwiftCodesByCountry() {
        // given
        String countryCode = "PL";
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setCountryISO2(countryCode);
        when(swiftCodeRepository.findByCountryISO2(countryCode)).thenReturn(Arrays.asList(swiftCode));

        // when
        List<SwiftCode> results = swiftCodeService.getByCountry(countryCode);

        // then
        assertFalse(results.isEmpty());
        assertEquals(countryCode, results.get(0).getCountryISO2());
    }
}