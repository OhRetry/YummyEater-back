package com.YammyEater.demo.service.food;

import com.YammyEater.demo.dto.food.FoodReviewConditionalRequest;
import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.YammyEater.demo.dto.food.FoodReviewRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodReviewService {
    Page<FoodReviewDto> getFoodReviewPageByCondition(Long foodId, FoodReviewConditionalRequest foodReviewConditionalRequest, Pageable pageable);
    Long registerFoodReview(Long userId, Long foodId, FoodReviewRegisterRequest foodReviewRegisterRequest);
    void deleteFoodReview(Long userId, Long reviewId);
}
