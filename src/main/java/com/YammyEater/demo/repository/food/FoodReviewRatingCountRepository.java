package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodReviewRatingCountRepository  extends JpaRepository<FoodReviewRatingCount, Long> {
}