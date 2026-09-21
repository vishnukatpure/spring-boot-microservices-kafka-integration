package com.kafka.microservice_producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.microservice_producer.services.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

	private final RedisService redisService;

	public RedisController(RedisService redisService) {
		this.redisService = redisService;
	}

	@PostMapping("/{key}/{value}")
	public String save(@PathVariable String key, @PathVariable String value) {

		redisService.save(key, value);
		return "Saved";
	}

	@GetMapping("/{key}")
	public String get(@PathVariable String key) {

		return redisService.get(key);
	}
}