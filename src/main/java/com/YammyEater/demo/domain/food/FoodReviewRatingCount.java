package com.YammyEater.demo.domain.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "FOOD_REVIEW_RATING_COUNT")
public class FoodReviewRatingCount {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "FOOD_REVIEW_RATING_COUNT_ID")
    private Long id;

    @Setter
    @Column(name = "RATE1")
    private Long rate1;

    @Setter
    @Column(name = "RATE2")
    private Long rate2;

    @Setter
    @Column(name = "RATE3")
    private Long rate3;

    @Setter
    @Column(name = "RATE4")
    private Long rate4;

    @Setter
    @Column(name = "RATE5")
    private Long rate5;

    @Builder
    public FoodReviewRatingCount(Long rate1, Long rate2, Long rate3, Long rate4, Long rate5) {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.rate4 = rate4;
        this.rate5 = rate5;
    }

    public FoodReviewRatingCount() {
    }

    public void increaseRatingCount(int rating) {
        addRatingCount(rating, 1);
    }

    public void decreaseRatingCount(int rating) {
        addRatingCount(rating, -1);
    }

    private void addRatingCount(int rating, long amount) {
        switch (rating) {
            case 1:
                this.rate1 += amount;
                break;
            case 2:
                this.rate2 += amount;
                break;
            case 3:
                this.rate3 += amount;
                break;
            case 4:
                this.rate4 += amount;
                break;
            case 5:
                this.rate5 += amount;
        }
    }
}
