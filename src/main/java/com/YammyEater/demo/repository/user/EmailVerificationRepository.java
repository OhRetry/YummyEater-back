package com.YammyEater.demo.repository.user;

import com.YammyEater.demo.domain.user.EmailVerification;


public interface EmailVerificationRepository {
    void save(String key, EmailVerification emailVerificationCode);
    EmailVerification findByKey(String key);
    boolean hasKey(String key);
    void setExpire(String key, int second);
    void deleteByKey(String key);
}
