package com.faster.user.app.auth.config;

import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    // 설정 구성을 먼저 진행한다.
    // Redis를 이용해서 Spring Ccahe를 사용할 때
    // Redis 관련 설정을 모아두는 클래스
    RedisCacheConfiguration configuration = RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues() // null을 캐싱하는지
        .entryTtl(Duration.ofSeconds(120)) // 기본 캐시 유지 시간 (Time To Live)
        .computePrefixWith(CacheKeyPrefix.simple()) // 캐시를 구분하는 접두사 설정
        .serializeValuesWith( // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
            SerializationPair.fromSerializer(RedisSerializer.java()) // 자바 바이트 코드로 저장
        );

    return RedisCacheManager
        .builder(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();
  }
}
