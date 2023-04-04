package com.YammyEater.demo.repository.food.queryDSL;

import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindFoodByCondition {
    Page<FoodSimpleResponse> findFoodSimpleResponsePageByCondition(
            FoodConditionalRequest foodConditionalRequest, Pageable pageable
    );
}
