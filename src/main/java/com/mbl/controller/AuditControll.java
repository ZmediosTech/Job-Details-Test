package com.mbl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbl.apiResponse.ApiResponse;
import com.mbl.entity.Audit;
import com.mbl.service.AuditService;

@RestController
@RequestMapping("/audit")
public class AuditControll {

    @Autowired
    private AuditService auditService;
    
    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAudits(){
	
	List<Audit> audits = auditService.getAudits();
	if(!audits.isEmpty()) {
	ApiResponse response= new ApiResponse(audits, "Retreived Successfully ");
	return ResponseEntity.ok(response);
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null, "No Audit Details Found!"));
    }
    
}
