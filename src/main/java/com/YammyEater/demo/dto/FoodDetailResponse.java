package com.YammyEater.demo.dto;

import com.YammyEater.demo.constant.FoodType;
import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.domain.FoodReviewRatingCount;
import java.util.List;

public record FoodDetailResponse(
        Long id,
        FoodType type,
        String name,
        String title,

        Long userId,
        String userName,

        String imgUrl,

        float rating,

        String ingredient,
        Long price,
        String maker,

        List<String> tags,

        NutrientDto nutrient,

        String content,

        FoodReviewRatingCountDto foodReviewRatingCount
) {
    static FoodDetailResponse of(Food food) {

        return new FoodDetailResponse(
                food.getId(),
                food.getType(),
                food.getName(),
                food.getTitle(),
                food.getUser().getId(),
                food.getUser().getUsername(),
                food.getImgUrl(),
                food.getRating(),
                food.getIngredient(),
                food.getPrice(),
                food.getMaker(),
                food.getTags().stream().map(x -> x.getTag().getName()).toList(),
                NutrientDto.of(food.getNutrient()),
                food.getArticle().getContent(),
                FoodReviewRatingCountDto.of(food.getFoodReviewRatingCount())
        );
    }
}
