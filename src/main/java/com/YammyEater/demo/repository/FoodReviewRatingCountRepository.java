package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.FoodReviewRatingCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodReviewRatingCountRepository  extends JpaRepository<FoodReviewRatingCount, Long> {
}
