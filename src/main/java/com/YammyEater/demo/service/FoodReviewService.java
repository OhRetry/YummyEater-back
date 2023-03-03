package com.YammyEater.demo.service;

import com.YammyEater.demo.dto.FoodReviewDto;
import com.YammyEater.demo.repository.FoodReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FoodReviewService {
    @Autowired
    FoodReviewRepository foodReviewRepository;

    public Page<FoodReviewDto> getFoodReviewPageByFoodId(Long foodId, Pageable pageable) {
        return foodReviewRepository.findByFoodId(foodId, pageable).map(FoodReviewDto::of);
    }
}
