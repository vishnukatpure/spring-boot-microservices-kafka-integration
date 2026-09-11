package com.kafka.microservice_producer.controller;

import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.dto.FileDownload;
import com.kafka.microservice_producer.services.FileOperationService;

import jakarta.validation.constraints.NotBlank;

@RequestMapping("/file/")
@RestController
public class FileResource {

	final FileOperationService fileOperationService;

	final FileOperationService fileOperationServiceS3;

	FileResource(FileOperationService fileOperationService, FileOperationService fileOperationServiceS3) {
		this.fileOperationService = fileOperationService;
		this.fileOperationServiceS3 = fileOperationServiceS3;
	}

	@GetMapping(value = "/{key}")
	public ResponseEntity<Resource> getFileByKey(@PathVariable @NotBlank(message = "Key is Empty") String key)
			throws FileNotFoundException {

		FileDownload file = fileOperationService.downloadFile(key);

		Resource resource = new InputStreamResource(file.inputStream());

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.contentType()))
				.contentLength(file.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.fileName() + "\"")
				.body(resource);
	}
}
