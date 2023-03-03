package com.YammyEater.demo.service;

import com.YammyEater.demo.constant.ErrorCode;
import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.FoodConditionalRequest;
import com.YammyEater.demo.dto.FoodDetailResponse;
import com.YammyEater.demo.dto.FoodSimpleResponse;
import com.YammyEater.demo.repository.FoodRepository;
import java.util.List;
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
