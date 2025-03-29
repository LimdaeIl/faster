package com.faster.user.app.auth.presentation.controller;

import com.faster.user.app.auth.application.usecase.AuthService;
import com.faster.user.app.auth.jwt.JwtProvider;
import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/internal/users")
@RequiredArgsConstructor
@RestController
public class RefreshTokenController {

  private static final Logger logger = LoggerFactory.getLogger(RefreshTokenController.class);
  private final AuthService authService;
  private final JwtProvider jwtProvider;

  @GetMapping("/store-refresh-tokens")
  public ResponseEntity<String> storeRefreshTokens(
      @RequestParam(value = "type", required = true) String type) {

    long startTime = System.currentTimeMillis();

    LongStream.rangeClosed(1, 100000).forEach(userId ->
        CompletableFuture.runAsync(() -> storeTokenForUser(userId, type))
    );

    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    logger.info("Refresh Tokens 저장 완료. 저장 방식: {} | 소요 시간: {} ms", type, duration);
    return ResponseEntity.ok("Refresh Tokens 저장 완료. 저장 방식: " + type + " | 소요 시간: " + duration + " ms");
  }

  private void storeTokenForUser(long userId, String type) {
    String refreshToken = jwtProvider.createRefreshToken(userId);

    if ("redis".equalsIgnoreCase(type)) {
      authService.storeRefreshToken(userId, refreshToken, true);
    } else if ("postgres".equalsIgnoreCase(type)) {
      authService.storeRefreshToken(userId, refreshToken, false);
    }
  }

  @GetMapping("/retrieve-refresh-tokens")
  public ResponseEntity<String> retrieveRefreshTokens(@RequestParam("type") String type) {
    long startTime = System.currentTimeMillis();

    LongStream.rangeClosed(1, 100000).forEach(userId ->
        CompletableFuture.runAsync(() -> retrieveTokenForUser(userId, type))
    );

    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    logger.info("Refresh Tokens 조회 완료. 조회 방식: {} | 소요 시간: {} ms", type, duration);
    return ResponseEntity.ok("Refresh Tokens 조회 완료. 조회 방식: " + type + " | 소요 시간: " + duration + " ms");
  }

  private void retrieveTokenForUser(long userId, String type) {
    if ("redis".equalsIgnoreCase(type)) {
      authService.getRefreshTokenFromRedis(userId);
    } else if ("postgres".equalsIgnoreCase(type)) {
      authService.getRefreshTokenFromPostgres(userId);
    }
  }
}