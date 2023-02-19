package com.YammyEater.demo.dto;

import com.YammyEater.demo.domain.FoodReviewRatingCount;

public record FoodReviewRatingCountDto(
        Long rate1,
        Long rate2,
        Long rate3,
        Long rate4,
        Long rate5
) {
    static FoodReviewRatingCountDto of(FoodReviewRatingCount foodReviewRatingCount){
        return new FoodReviewRatingCountDto(
                foodReviewRatingCount.getRate1(),
                foodReviewRatingCount.getRate2(),
                foodReviewRatingCount.getRate3(),
                foodReviewRatingCount.getRate4(),
                foodReviewRatingCount.getRate5()
        );
    }
}
