package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository  extends JpaRepository<Nutrient, Long> {
}
