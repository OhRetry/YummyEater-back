package com.YammyEater.demo.service.user;

import com.YammyEater.demo.dto.user.OAuthUserJoinRequest;

public interface UserJoinService {
    void sendVerifyingEmail(String email);

    boolean verifyEmailByCode(String code);

    void joinByCode(String code, String email, String username, String password);
    Long joinByOAuth(OAuthUserJoinRequest oAuthUserJoinRequest);
}
