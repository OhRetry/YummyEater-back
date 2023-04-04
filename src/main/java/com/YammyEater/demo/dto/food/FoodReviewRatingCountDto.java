package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.domain.food.FoodReviewRatingCount;

public record FoodReviewRatingCountDto(
        Long rate1,
        Long rate2,
        Long rate3,
        Long rate4,
        Long rate5
) {
    public static FoodReviewRatingCountDto of(FoodReviewRatingCount foodReviewRatingCount){
        return new FoodReviewRatingCountDto(
                foodReviewRatingCount.getRate1(),
                foodReviewRatingCount.getRate2(),
                foodReviewRatingCount.getRate3(),
                foodReviewRatingCount.getRate4(),
                foodReviewRatingCount.getRate5()
        );
    }
}
