package com.kafka.microservice_producer.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.kafka.microservice_producer.dto.FileDownload;

public interface FileOperationService {

	public String uploadFile(MultipartFile file) throws IOException;

	FileDownload downloadFile(String key) throws FileNotFoundException;

}
