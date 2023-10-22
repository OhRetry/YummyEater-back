package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.constant.food.FoodRankingPeriod;
import com.YammyEater.demo.constant.food.FoodType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class FoodRankingRepositoryImpl implements FoodRankingRepository {

    private String KEY_RANK = "FR";

    private final RedisTemplate redisTemplate;
    private final ZSetOperations<String, Long> zSetOperations;

    public FoodRankingRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public void increaseFoodViews(Long foodId, FoodType foodType) {
        zSetOperations.incrementScore(getKey(FoodRankingPeriod.DAY,foodType), foodId, 1);
        zSetOperations.incrementScore(getKey(FoodRankingPeriod.WEEK,foodType), foodId, 1);
        zSetOperations.incrementScore(getKey(FoodRankingPeriod.MONTH,foodType), foodId, 1);
    }

    @Override
    public List<Long> getTopKRankFood(FoodRankingPeriod period, FoodType foodType, int k) {
        Set<Long> rset = zSetOperations.reverseRange(getKey(period, foodType), 0, k);
        return rset.stream().toList();
    }

    private String getKey(FoodRankingPeriod period, FoodType foodType) {
        return KEY_RANK + "_" + period + "_" + foodType;
    }
}
