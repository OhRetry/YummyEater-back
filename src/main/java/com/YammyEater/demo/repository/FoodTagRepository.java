package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.FoodTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTagRepository  extends JpaRepository<FoodTag, Long> {
}
