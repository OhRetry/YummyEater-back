package com.YammyEater.demo.service.user;

import com.YammyEater.demo.constant.user.OAuthProvider;
import com.YammyEater.demo.dto.user.oauth.OAuthJoinTokenSubject;

public interface JwtTokenProvider {
    String createAccessToken(Long userId);
    String createRefreshToken(Long userId, String accessToken);
    String refreshAccessToken(String refreshToken, String accessToken);

    Long validateAccessTokenAndGetUserId(String accessToken);
    Long validateRefreshTokenAndGetUserId(String refreshToken, String lastAccessToken);

    String createOAuthJoinToken(OAuthJoinTokenSubject oAuthJoinTokenSubject);
    OAuthJoinTokenSubject validateOAuthJoinTokenAndGetSubject(String joinToken);

}
