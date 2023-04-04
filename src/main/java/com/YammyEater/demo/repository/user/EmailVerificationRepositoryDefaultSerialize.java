package com.YammyEater.demo.repository.user;

import com.YammyEater.demo.domain.user.EmailVerification;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class EmailVerificationRepositoryDefaultSerialize implements EmailVerificationRepository {
    String prefix_key = "EV_";

    RedisTemplate redisTemplate;
    ValueOperations valueOperations;

    public EmailVerificationRepositoryDefaultSerialize(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(String key, EmailVerification emailVerificationCode) {
        valueOperations.set(getFullKey(key), emailVerificationCode);
    }

    @Override
    public EmailVerification findByKey(String key) {
        return (EmailVerification) valueOperations.get(getFullKey(key));
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(getFullKey(key));
    }

    @Override
    public void setExpire(String key, int second) {
        if(second < 0) {
            redisTemplate.persist(getFullKey(key));
        }
        else {
            redisTemplate.expire(getFullKey(key), second, TimeUnit.SECONDS);
        }
    }

    @Override
    public void deleteByKey(String key) {
        redisTemplate.delete(getFullKey(key));
    }

    private String getFullKey(String key) {
        return prefix_key + key;
    }
}
