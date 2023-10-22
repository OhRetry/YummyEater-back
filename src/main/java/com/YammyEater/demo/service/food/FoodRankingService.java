package com.YammyEater.demo.service.food;

import com.YammyEater.demo.constant.food.FoodRankingPeriod;
import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.dto.food.FoodRankResponse;
import java.util.List;

public interface FoodRankingService {
    List<FoodRankResponse> getFoodRanking(FoodRankingPeriod period, FoodType foodType, int k);
}
