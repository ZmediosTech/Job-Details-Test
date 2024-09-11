package com.mbl.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbl.entity.FileDetails;
import com.mbl.repository.FileDetailsRepository;

@Service
public class FileService {

    @Autowired
    FileDetailsRepository fileRepository;

    // save in database
    public FileDetails save(FileDetails fileDetails) {

	FileDetails file = fileRepository.save(fileDetails);
	return file;
    }

    // download the file
    public FileDetails downloadFile(Long fileId) {
	Optional<FileDetails> fileDetailsOptional = fileRepository.findById(fileId);
	if (!fileDetailsOptional.isPresent()) {

	    return null;
	}
	return fileDetailsOptional.get();
    }
}
