package com.kafka.microservice_producer.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kafka.microservice_producer.dto.FileDownload;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@ConditionalOnProperty(name = "file-storage.type", havingValue = "s3")
public class S3BucketService implements FileOperationService {

	private final S3Client s3Client;

	private final String bucketName;

	public S3BucketService(S3Client s3Client, @Value("${aws.s3.bucket}") String bucketName) {
		this.s3Client = s3Client;
		this.bucketName = bucketName;
	}

	@Override
	public String uploadFile(MultipartFile file) throws IOException {

		String key = "documents/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

		PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(key)
				.contentType(file.getContentType()).build();

		s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

		return key;
	}

	@Override
	public FileDownload downloadFile(String key) {

		GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(key).build();

		ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(request);

		GetObjectResponse response = inputStream.response();

		String fileName = key.substring(key.lastIndexOf("/") + 1);

		return new FileDownload(inputStream, fileName, response.contentType(), response.contentLength());
	}
}
