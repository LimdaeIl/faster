package com.faster.user.app.auth.application.usecase;


import com.faster.user.app.auth.application.dto.SaveUserRequestDto;
import com.faster.user.app.auth.application.dto.SignInUserRequestDto;
import com.faster.user.app.auth.presentation.dto.response.SaveUserResponseDto;
import com.faster.user.app.auth.presentation.dto.response.SignInUserResponseDto;

public interface AuthService {
  SaveUserResponseDto createUser(SaveUserRequestDto requestDto);
  SignInUserResponseDto signInUser(SignInUserRequestDto requestDto);
  void logout(Long userId);
  String generateNewAccessToken(Long userId);
  void storeRefreshToken(long userId, String refreshToken, boolean isRedis);
  String getRefreshTokenFromRedis(long userId);
  String getRefreshTokenFromPostgres(long userId);
}
