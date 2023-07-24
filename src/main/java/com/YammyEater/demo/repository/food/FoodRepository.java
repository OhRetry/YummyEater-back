package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.repository.food.queryDSL.FindFoodByCondition;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface FoodRepository extends JpaRepository<Food, Long>, FindFoodByCondition {

    @EntityGraph(attributePaths = {"user", "categories.category"})
    List<Food> findAll();

    @EntityGraph(attributePaths = {"user", "categories.category", "nutrient", "article", "foodReviewRatingCount"})
    Optional<Food> findEagerById(Long id);

    Optional<Food> findById(Long id);

    @Modifying
    @Query(value = "UPDATE FROM Food food SET food.views = food.views + 1 WHERE food.id = :id")
    void increaseViews(@Param("id") Long id);
}
