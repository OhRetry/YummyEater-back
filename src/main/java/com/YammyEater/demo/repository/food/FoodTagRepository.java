package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodCategory;
import com.YammyEater.demo.domain.food.FoodTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodTagRepository extends JpaRepository<FoodTag, Long> {
    @Modifying
    @Query(value = "DELETE FROM FoodTag ft WHERE ft.food = :food")
    void deleteAllByFood(@Param("food") Food food);
}
