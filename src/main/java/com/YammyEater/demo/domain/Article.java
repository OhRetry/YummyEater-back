package com.YammyEater.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE")
public class Article {
    @Id
    @Column(name = "FOOD_ID")
    private Long id;

    @Column(name = "CONTENT")
    private String content;
}
