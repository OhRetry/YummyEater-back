package com.YammyEater.demo.service.user;

public interface UserJoinService {
    void sendVerifyingEmail(String email);

    boolean verifyEmailByCode(String code);

    void joinByCode(String code, String email, String username, String password);
}
