package com.kafka.microservice_producer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kafka.microservice_producer.model.FileEntity;

public interface FileRepository extends CrudRepository<FileEntity, Long> {

	Optional<FileEntity> findByFileId(String key);

}
