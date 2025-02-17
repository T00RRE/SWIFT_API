package com.example.swift_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "swift_codes")
public class SwiftCode {
    @Id
    @Column(length = 11)
    private String swiftCode;
    
    @Column(nullable = false, length = 255)  // zwiększona długość
    private String name;
    
    @Column(length = 2, nullable = false)
    private String countryISO2;
    
    @Column(nullable = false, length = 100)  // zwiększona długość
    private String countryName;
    
    @Column(length = 500)  // zwiększona długość
    private String address;
    
    @Column(length = 100)  // zwiększona długość
    private String townName;
    
    @Column(length = 100)
    private String codeType;
    
    private boolean headquarter;
    
    @ManyToOne
    @JoinColumn(name = "headquarter_swift")
    private SwiftCode headquarterBank;
}