package com.YammyEater.demo.repository.queryDSL.FoodRepository;

import com.YammyEater.demo.dto.FoodConditionalRequest;
import com.YammyEater.demo.dto.FoodSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindFoodByCondition {
    Page<FoodSimpleResponse> findFoodSimpleResponsePageByCondition(
            FoodConditionalRequest foodConditionalRequest, Pageable pageable
    );
}
