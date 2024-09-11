package com.mbl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mbl.apiResponse.ApiResponse;
import com.mbl.entity.JobDetails;
import com.mbl.exception.ResourceNotFoundException;
import com.mbl.service.impl.JobDetailsServiceImpl;

@RestController
@RequestMapping("/job-details")
public class JobDetailsController {

    @Autowired
    private JobDetailsServiceImpl jobDetailsService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody JobDetails entity) throws Exception {
	JobDetails jobDetails = jobDetailsService.addJobDetails(entity);
	return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(jobDetails, "Added successfully"));

    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllDetails() {
	List<JobDetails> allDetails = jobDetailsService.getJobDetails();

	if (!allDetails.isEmpty()) {
	    ApiResponse response = new ApiResponse(allDetails, "Successfully retrieved all details");
	    return ResponseEntity.ok(response);
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(allDetails, "No Details Found"));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse> getDetailById(@PathVariable Long id) {
	JobDetails entityById = jobDetailsService.getJobById(id);

	if (entityById != null) {
	    ApiResponse response = new ApiResponse(entityById, "Successfully get By Id: " + id);
	    return ResponseEntity.ok(response);
	}

	throw new ResourceNotFoundException("JobDetail not of of id: " + id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteJobDetailsById(@PathVariable Long id) throws Exception {
	jobDetailsService.deleteJobDetails(id);
	ApiResponse response = new ApiResponse(null, "Deleted Successfully");

	return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<JobDetails> updateJobDetails(@RequestBody JobDetails entity) throws Exception {

	JobDetails updateJobDetails = jobDetailsService.updateJobDetails(entity);

	if (updateJobDetails != null) {
	    return ResponseEntity.ok(updateJobDetails);
	}
	throw new ResourceNotFoundException("JobDetails Not Found");
    }

    // ........... for file uploading............

    @PostMapping("/file/upload/{jobId}")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file, @PathVariable Long jobId)
	    throws Exception {

	String uploadFilePath = jobDetailsService.uploadFile(file, jobId);
	if (uploadFilePath != null) {
	    return ResponseEntity.ok("File uploaded successfully! " + uploadFilePath);
	}
	return ResponseEntity.internalServerError().body("Falied to upload");
    }

}
