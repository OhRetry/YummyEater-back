package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.FoodReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodReviewRepository  extends JpaRepository<FoodReview, Long> {
}
