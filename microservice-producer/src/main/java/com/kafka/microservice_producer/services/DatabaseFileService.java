package com.kafka.microservice_producer.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kafka.microservice_producer.custom.exception.FileStorageException;
import com.kafka.microservice_producer.dto.FileDownload;
import com.kafka.microservice_producer.model.FileEntity;
import com.kafka.microservice_producer.repository.FileRepository;

@Service
@Primary
public class DatabaseFileService implements FileOperationService {

	private final FileRepository fileRepository;

	public DatabaseFileService(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		String key = UUID.randomUUID().toString();

		FileEntity entity = new FileEntity();
		entity.setFileId(key);
		entity.setFileName(file.getOriginalFilename());
		entity.setContentType(file.getContentType());
		entity.setFileSize(file.getSize());
		entity.setFileData(file.getBytes());

		FileEntity saved = fileRepository.save(entity);

		return saved.getFileId();
	}

	@Override
	public FileDownload downloadFile(String key) throws FileStorageException {

		FileEntity entity = fileRepository.findByFileId(key).orElseThrow(() -> new FileStorageException(key));

		InputStream inputStream = new ByteArrayInputStream(entity.getFileData());

		return new FileDownload(inputStream, entity.getFileName(), entity.getContentType(), entity.getFileSize());
	}

}
