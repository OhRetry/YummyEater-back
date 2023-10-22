package com.YammyEater.demo.service.food;

import com.YammyEater.demo.constant.food.FoodRankingPeriod;
import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.dto.food.FoodRankResponse;
import com.YammyEater.demo.repository.food.FoodRankingRepository;
import com.YammyEater.demo.repository.food.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodRankingServiceImpl implements FoodRankingService{

    private final FoodRankingRepository foodRankingRepository;
    private final FoodRepository foodRepository;

    @Override
    @Transactional
    public List<FoodRankResponse> getFoodRanking(FoodRankingPeriod period, FoodType foodType, int k) {
        List<Long> ids = foodRankingRepository.getTopKRankFood(period, foodType, k);
        List<Food> foods = foodRepository.findWithUserByIDs(ids);
        return ids.stream().map(foodRepository::getById).map(FoodRankResponse::of).toList();
    }
}
