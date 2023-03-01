package com.YammyEater.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "FOOD_REVIEW")
public class FoodReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_REVIEW_ID")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Setter
    @Column(name = "RATING")
    private int rating;

    @Setter
    @Column(name = "CONTENT")
    private String content;

    @Builder
    public FoodReview(Food food, User writer, int rating, String content) {
        this.food = food;
        this.user = writer;
        this.rating = rating;
        this.content = content;
    }

    public FoodReview() {
    }
}


