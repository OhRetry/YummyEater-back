package com.YammyEater.demo.dto;

import com.YammyEater.demo.domain.FoodReview;
import java.time.format.DateTimeFormatter;

public record FoodReviewDto(
        Long id,
        Long userId,
        String userName,
        int rating,
        String content,
        String createdAt,
        String lastModifiedAt
) {
    public static FoodReviewDto of(FoodReview foodReview) {
        return new FoodReviewDto(
                foodReview.getId(),
                foodReview.getUser().getId(),
                foodReview.getUser().getUsername(),
                foodReview.getRating(),
                foodReview.getContent(),
                foodReview.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME),
                foodReview.getLastModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}
