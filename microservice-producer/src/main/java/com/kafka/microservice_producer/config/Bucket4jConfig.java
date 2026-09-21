package com.kafka.microservice_producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;

@Configuration
public class Bucket4jConfig {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	@Bean
	RedisClient redisClient() {
		return RedisClient.create("redis://" + redisHost + ":" + redisPort);
	}

	@Bean
	StatefulRedisConnection<String, byte[]> bucketRedisConnection(RedisClient redisClient) {
		RedisCodec<String, byte[]> codec = RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE);
		return redisClient.connect(codec);
	}

	@Bean
	ProxyManager<String> bucketProxyManager(StatefulRedisConnection<String, byte[]> connection) {
		return Bucket4jLettuce.casBasedBuilder(connection).build();
	}
}
