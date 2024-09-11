package com.mbl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String containerNo;
    private String sizeType;
    private String sealNo;
    private String totalNoOfPackages;
    private String packageType;
    private String totalGrWt;
    private String volume;
    
    private Boolean haz;
    private String agentSealNo;
    
}
