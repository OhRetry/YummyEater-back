package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}

