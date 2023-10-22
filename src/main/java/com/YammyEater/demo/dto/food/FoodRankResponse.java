package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.domain.food.Food;
import lombok.Builder;

@Builder
public record FoodRankResponse(
        Long id,
        FoodType type,
        String imgURL,
        String title,
        Long userId,
        String userName,
        float rating,
        int views
) {
    public static FoodRankResponse of(Food food) {
        return FoodRankResponse.builder()
                .id(food.getId())
                .type(food.getType())
                .imgURL(food.getImgUrl())
                .title(food.getTitle())
                .userId(food.getUser().getId())
                .userName(food.getUser().getUsername())
                .rating(food.getRating())
                .views(food.getViews())
                .build();
    }
}
