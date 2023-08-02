package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodResourceRepository extends JpaRepository<FoodResource, String> {
    List<FoodResource> findAllByFood(Food food);
    void deleteAllByFood(Food food);

}
