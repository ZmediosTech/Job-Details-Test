package com.mbl.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /// ............ Store file in fileSystem....
    public String storeFile(MultipartFile file) throws Exception {

	// Create directory if it doesn't exist
	Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

	Files.createDirectories(fileStorageLocation);

	// Resolve the file path
	Path targetLocation = fileStorageLocation.resolve(file.getOriginalFilename());
	Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	return targetLocation.toString();
    }

    // Method to load a file by name
    public Path loadFileAsResource(String fileName) {
	Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
	return filePath;
    }
}
