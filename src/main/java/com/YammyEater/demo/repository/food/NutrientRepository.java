package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository  extends JpaRepository<Nutrient, Long> {
}
