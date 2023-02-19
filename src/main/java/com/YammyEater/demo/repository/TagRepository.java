package com.YammyEater.demo.repository;

import com.YammyEater.demo.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

