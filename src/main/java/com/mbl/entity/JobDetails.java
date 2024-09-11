package com.mbl.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long masterBLNo;
    private String agentName;
    private String vessel;
    private String placeOfReceipt;
    private String pdo;
    private String shipmentType;
    private LocalDateTime arrivalDate;
    private String destuffingPoint;
    private String etd;
    private LocalDateTime masterBlDate;

    private String line;

    private String voyage;
    private String pol;

    private String fpd;
    private String coloderName;
    private String agentRefNo;

    private String remarks;
    private String coloadMblNo;

    // File details
    @OneToOne(mappedBy = "jobDetail", cascade = CascadeType.ALL)
    private FileDetails filedetails;

    // .........Mapping fields............

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobDetail_Id", referencedColumnName = "id")
    private List<ContainerDetails> containerDetails;
}
