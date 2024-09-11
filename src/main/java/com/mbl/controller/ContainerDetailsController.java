package com.mbl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbl.apiResponse.ApiResponse;
import com.mbl.entity.ContainerDetails;
import com.mbl.service.impl.ContainerDetailsService;

@RestController
@RequestMapping("/container-details")
public class ContainerDetailsController {

    @Autowired
    private ContainerDetailsService containerService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllContainerDetails() {

	List<ContainerDetails> containters = containerService.getContainters();

	if (!containters.isEmpty()) {
	    ApiResponse response = new ApiResponse(containters, "successfully retreived");

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null, "No Audit Found!"));
    }
}
