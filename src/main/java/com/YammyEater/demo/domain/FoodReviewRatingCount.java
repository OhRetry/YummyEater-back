package com.YammyEater.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FOOD_REVIEW_RATING_COUNT")
public class FoodReviewRatingCount {
    @Id
    @Column(name = "FOOD_ID")
    private Long id;

    @Column(name = "RATE1")
    private Long rate1;

    @Column(name = "RATE2")
    private Long rate2;

    @Column(name = "RATE3")
    private Long rate3;

    @Column(name = "RATE4")
    private Long rate4;

    @Column(name = "RATE5")
    private Long rate5;
}
