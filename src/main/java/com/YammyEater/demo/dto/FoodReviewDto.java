package com.YammyEater.demo.dto;

import com.YammyEater.demo.domain.FoodReview;

public record FoodReviewDto(
        Long id,
        Long userId,
        String userName,
        int rating,
        String content
) {
    public static FoodReviewDto of(FoodReview foodReview) {
        return new FoodReviewDto(
                foodReview.getId(),
                foodReview.getUser().getId(),
                foodReview.getUser().getUsername(),
                foodReview.getRating(),
                foodReview.getContent()
        );
    }
}
