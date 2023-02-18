package com.YammyEater.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ARTICLE")
public class Article {
    @Id
    @Column(name = "FOOD_ID")
    private Long id;

    @Column(name = "CONTENT")
    private String content;
}
