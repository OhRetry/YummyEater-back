package com.YammyEater.demo.service.user;

public interface JwtTokenProvider {
    String createAccessToken(Long userId);

    Long validateAccessTokenAndGetUserId(String accessToken);
}
