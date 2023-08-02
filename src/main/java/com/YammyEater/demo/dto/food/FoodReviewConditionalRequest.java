package com.YammyEater.demo.dto.food;


public record FoodReviewConditionalRequest (
        Long userId,
        String userName,
        Integer rating
) {
}