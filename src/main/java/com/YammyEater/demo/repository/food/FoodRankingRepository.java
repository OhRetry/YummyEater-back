package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.constant.food.FoodRankingPeriod;
import com.YammyEater.demo.constant.food.FoodType;

import java.util.List;

public interface FoodRankingRepository {

    public void increaseFoodViews(Long foodId, FoodType foodType);
    public List<Long> getTopKRankFood(FoodRankingPeriod period, FoodType foodType, int k);
}
