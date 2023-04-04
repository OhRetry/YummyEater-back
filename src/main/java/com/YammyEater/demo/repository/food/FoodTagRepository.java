package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.FoodTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTagRepository  extends JpaRepository<FoodTag, Long> {
}
