package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.Article;
import com.YammyEater.demo.domain.Food;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
