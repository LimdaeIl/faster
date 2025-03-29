package com.faster.user.app.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_refresh_tokens")
public class RefreshTokenEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;
  private String refreshToken;
  private LocalDateTime expiryDate;


  public RefreshTokenEntity(Long userId, String refreshToken) {
    this.userId = userId;
    this.refreshToken = refreshToken;
    this.expiryDate = LocalDateTime.now().plusDays(7); // 토큰 유효기간 7일
  }
}