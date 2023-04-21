package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReview;
import com.YammyEater.demo.repository.food.queryDSL.FindFoodReviewByCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodReviewRepository extends JpaRepository<FoodReview, Long>, FindFoodReviewByCondition {
    @Query(value = "SELECT fr FROM FoodReview fr JOIN FETCH fr.user user WHERE fr.food.id = :foodId",
            countQuery = "SELECT count(fr) FROM FoodReview fr WHERE fr.food.id = :foodId")
    Page<FoodReview> findPageEagerByFoodId(@Param("foodId") Long foodId, Pageable pageable);


    @Modifying
    @Query(value = "DELETE FROM FoodReview fr WHERE fr.food = :food")
    void deleteAllByFood(@Param("food") Food food);
}
