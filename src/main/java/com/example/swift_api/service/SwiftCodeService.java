package com.example.swift_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.swift_api.exception.SwiftCodeException;
import com.example.swift_api.model.SwiftCode;
import com.example.swift_api.repository.SwiftCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    public SwiftCode getBySwiftCode(String swiftCode) {
    return swiftCodeRepository.findById(swiftCode)
            .orElseThrow(() -> new SwiftCodeException("Swift code not found: " + swiftCode));
}

    public List<SwiftCode> getByCountry(String countryISO2) {
        return swiftCodeRepository.findByCountryISO2(countryISO2.toUpperCase());
    }

    public SwiftCode save(SwiftCode swiftCode) {
        swiftCode.setHeadquarter(swiftCode.getSwiftCode().endsWith("XXX"));
        return swiftCodeRepository.save(swiftCode);
    }

    public void deleteBySwiftCode(String swiftCode) {
        swiftCodeRepository.deleteById(swiftCode);
    }
    
}