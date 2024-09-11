package com.mbl.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mbl.entity.JobDetails;

public interface JobDetailsService {

    JobDetails addJobDetails(JobDetails jobDetails);
    List<JobDetails> getJobDetails();
    JobDetails getJobById(Long id);
    void deleteJobDetails(Long id);
    JobDetails updateJobDetails(JobDetails jobDetails);
    
    //file uploading......
    String uploadFile(MultipartFile file, Long id);
    
}
