package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

