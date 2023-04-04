package com.YammyEater.demo.service.food;

import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodDetailResponse;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import com.YammyEater.demo.repository.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodService {
    @Autowired
    FoodRepository foodRepository;

    public Page<FoodSimpleResponse> findFoodByCondition(FoodConditionalRequest foodConditionalRequest, Pageable pageable) {
        return foodRepository.findFoodSimpleResponsePageByCondition(foodConditionalRequest, pageable);
    }

    @Transactional(readOnly = true)
    public FoodDetailResponse findFoodById(Long id) {
        FoodDetailResponse res = foodRepository.findById(id).map(FoodDetailResponse::of).orElse(null);
        return res;
    }
}
