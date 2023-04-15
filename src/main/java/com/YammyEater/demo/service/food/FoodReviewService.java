package com.YammyEater.demo.service.food;

import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.YammyEater.demo.dto.food.FoodReviewRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodReviewService {
    Page<FoodReviewDto> getFoodReviewPageByFoodId(Long foodId, Pageable pageable);

    Long registerFoodReview(Long userId, Long foodId, FoodReviewRegisterRequest foodReviewRegisterRequest);
}
