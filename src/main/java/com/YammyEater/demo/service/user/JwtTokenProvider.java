package com.YammyEater.demo.service.user;

public interface JwtTokenProvider {
    String create(Long userId);

    Long validateAndGetUserId(String accessToken);
}
