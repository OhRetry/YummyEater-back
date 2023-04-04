package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.repository.food.queryDSL.FindFoodByCondition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>, FindFoodByCondition {

    @EntityGraph(attributePaths = {"user", "tags.tag"})
    List<Food> findAll();

    @EntityGraph(attributePaths = {"user", "tags.tag", "nutrient", "article", "foodReviewRatingCount"})
    Optional<Food> findById(Long id);
}
