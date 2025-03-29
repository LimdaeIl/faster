package com.faster.user.app.auth.application.usecase;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RedisService {

  private final StringRedisTemplate redisTemplate;

  public void saveRefreshToken(Long userId, String refreshToken) {
    String key = "refreshToken:" + userId;
    redisTemplate.opsForHash().put(key, "token", refreshToken);
  }

  public String getRefreshToken(long userId) {
    String key = "refresh_token:" + userId;
    return redisTemplate.opsForValue().get(key);
  }

}