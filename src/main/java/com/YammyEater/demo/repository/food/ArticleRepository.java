package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository  extends JpaRepository<Article, Long> {
}
