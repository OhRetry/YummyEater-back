package com.YammyEater.demo.service.food;

import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodDetailResponse;
import com.YammyEater.demo.dto.food.FoodRegisterRequest;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {
    Page<FoodSimpleResponse> findFoodByCondition(FoodConditionalRequest foodConditionalRequest, Pageable pageable);

    FoodDetailResponse findFoodById(Long id);

    Long registerFood(Long userId, FoodRegisterRequest foodRegisterRequest);

    void deleteFood(Long userId, Long foodId);
}
