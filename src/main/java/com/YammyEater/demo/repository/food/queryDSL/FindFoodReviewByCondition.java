package com.YammyEater.demo.repository.food.queryDSL;

import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodReviewConditionalRequest;
import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindFoodReviewByCondition {
    Page<FoodReviewDto> findFoodReviewPageByCondition(
            Long foodId,
            FoodReviewConditionalRequest foodReviewConditionalRequest,
            Pageable pageable
    );
}
