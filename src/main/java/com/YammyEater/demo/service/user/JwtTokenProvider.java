package com.YammyEater.demo.service.user;

public interface JwtTokenProvider {
    String createAccessToken(Long userId);
    String createRefreshToken(Long userId, String accessToken);
    String refreshAccessToken(String refreshToken, String accessToken);

    Long validateAccessTokenAndGetUserId(String accessToken);
    Long validateRefreshTokenAndGetUserId(String refreshToken, String lastAccessToken);
}
