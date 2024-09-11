package com.mbl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mbl.entity.Audit;
import com.mbl.entity.FileDetails;
import com.mbl.entity.JobDetails;
import com.mbl.exception.ResourceNotFoundException;
import com.mbl.repository.JobDetailsRepository;
import com.mbl.service.FileService;
import com.mbl.service.JobDetailsService;
import com.mbl.util.EntityComparator;
import com.mbl.util.FileUtil;

@Service
public class JobDetailsServiceImpl implements JobDetailsService {

    @Autowired
    private JobDetailsRepository jobDetailsRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private AuditServiceImpl auditServiceImpl;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public JobDetails addJobDetails(JobDetails jobDetails) {

	JobDetails savedDetails = jobDetailsRepository.save(jobDetails);

	// ........... for AUDIT............
	Map<String, String> changes = new HashMap<String, String>();
	changes.put("All Fields", "added , added");

	// ..........JOBdetails audit.........
	List<Audit> auditSave = auditServiceImpl.auditSave("JobDetails", savedDetails.getId(), "ADDED", changes);

	for (int i = 0; i < jobDetails.getContainerDetails().size(); i++) {
	    auditSave.addAll(auditServiceImpl.auditSave("ContainerDetails",
		    EntityComparator.getId(savedDetails.getContainerDetails().get(i)), "ADDED", changes));
	}

	// save audit to database................
	auditServiceImpl.saveAuditDetails(auditSave);

	// -------------------------------------------------

	return savedDetails;
    }

    @Override
    public JobDetails updateJobDetails(JobDetails updateJobDetails) {

	Optional<JobDetails> optionalJobDetails = jobDetailsRepository.findById(updateJobDetails.getId());
	if (optionalJobDetails.isPresent()) {
	    JobDetails existingJobDetails = optionalJobDetails.get();

	    // ---------for Audit..................
	    List<Audit> auditList = new ArrayList<>();

	    // ............ normal fields...............

	    Map<String, String> jobDetailsMap = EntityComparator.compareFields(existingJobDetails, updateJobDetails);
	    auditList.addAll(
		    auditServiceImpl.auditSave("JobDetails", updateJobDetails.getId(), "UPDATE", jobDetailsMap));

	    // ............. for Collection fields.............
	    // .............. save containerDetails audit.........
	    for (int i = 0; i < updateJobDetails.getContainerDetails().size(); i++) {

		Map<String, String> containerMap = EntityComparator.compareFields(
			existingJobDetails.getContainerDetails().get(i), updateJobDetails.getContainerDetails().get(i));

		auditList.addAll(auditServiceImpl.auditSave("ContainerDetails",
			EntityComparator.getId(existingJobDetails.getContainerDetails().get(i)), "UPDATE",
			containerMap));
	    }

	    auditServiceImpl.saveAuditDetails(auditList);
	    // --------------------------------------------------

	    return jobDetailsRepository.save(updateJobDetails);
	}
	return null;
    }

    @Override
    public List<JobDetails> getJobDetails() {
	return jobDetailsRepository.findAll();
    }

    @Override
    public JobDetails getJobById(Long id) {
	Optional<JobDetails> optionalDetails = jobDetailsRepository.findById(id);
	if (optionalDetails.isPresent()) {
	    return optionalDetails.get();
	}
	return null;
    }

    @Override
    public void deleteJobDetails(Long id) {
	jobDetailsRepository.deleteById(id);
    }

    // --------------------File uploading......................
    @Override
    public String uploadFile(MultipartFile file, Long jobId) {

	Optional<JobDetails> jobDetailsOptional = jobDetailsRepository.findById(jobId);

	try {
	    if (jobDetailsOptional.isEmpty()) {
		throw new ResourceNotFoundException("JobDetails not found of id: " + jobId);
	    }
	    JobDetails jobDetails = jobDetailsOptional.get();

	    String filePath = fileUtil.storeFile(file);

	    // Save the file details in the database
	    FileDetails fileDetails = FileDetails.builder().fileName(file.getOriginalFilename())
		    .fileType(file.getContentType()).filePath(filePath).jobDetail(jobDetails).build();

	    FileDetails saveFile = fileService.save(fileDetails);

	    // ........ audit details save.........
	    Map<String, String> changes = new HashMap<>();
	    changes.put("All fields", "file upload, file upload");
	    List<Audit> auditFile = auditServiceImpl.auditSave("FileDetails", saveFile.getId(), "UploadFile", changes);
	    auditServiceImpl.saveAuditDetails(auditFile);
	    // ---------------------------------------------------

	    if (saveFile != null) {
		return filePath;
	    }
	} catch (Exception e) {
	    throw new InternalError("Something went wrong");
	}
	return null;
    }

}
