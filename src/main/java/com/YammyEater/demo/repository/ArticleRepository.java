package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository  extends JpaRepository<Article, Long> {
}
