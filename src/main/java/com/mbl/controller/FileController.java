package com.mbl.controller;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbl.entity.FileDetails;
import com.mbl.service.FileService;
import com.mbl.util.FileUtil;

@RestController
@RequestMapping("job-details/file")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private FileUtil fileUtil;

    // file download
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws Exception {

	FileDetails downloadFile = fileService.downloadFile(fileId);

	Path filePath = fileUtil.loadFileAsResource(downloadFile.getFileName());

	Resource resource = new UrlResource(filePath.toUri());
	if (resource.exists()) {
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType(downloadFile.getFileType()))
		    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		    .body(resource);
	} else {
	    return ResponseEntity.notFound().build();
	}
    }

    
}
