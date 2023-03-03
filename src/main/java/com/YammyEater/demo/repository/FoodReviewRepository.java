package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.FoodReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodReviewRepository extends JpaRepository<FoodReview, Long> {
    @Query(value = "SELECT fr FROM FoodReview fr JOIN FETCH fr.user user WHERE fr.food.id = :foodId",
            countQuery = "SELECT count(fr) FROM FoodReview fr WHERE fr.food.id = :foodId")
    Page<FoodReview> findByFoodId(@Param("foodId") Long foodId, Pageable pageable);
}
