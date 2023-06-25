package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.domain.food.Food;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record FoodDetailResponse(
        Long id,
        FoodType type,
        String title,

        Long userId,
        String userName,

        String imgUrl,

        float rating,

        Integer servings,
        Float amount,

        String ingredient,
        Long price,
        String maker,

        List<String> categories,
        List<String> tags,
        NutrientDto nutrient,

        String content,

        FoodReviewRatingCountDto foodReviewRatingCount,

        String createdAt,
        String lastModifiedAt
) {
   public static FoodDetailResponse of(Food food) {
        return new FoodDetailResponse(
                food.getId(),
                food.getType(),
                food.getTitle(),
                food.getUser().getId(),
                food.getUser().getUsername(),
                food.getImgUrl(),
                food.getRating(),
                food.getServings(),
                food.getAmount(),
                food.getIngredient(),
                food.getPrice(),
                food.getMaker(),
                food.getCategories().stream().map(x -> x.getCategory().getName()).toList(),
                food.getTags().stream().map(x -> x.getTag()).toList(),
                NutrientDto.of(food.getNutrient()),
                food.getArticle().getContent(),
                FoodReviewRatingCountDto.of(food.getFoodReviewRatingCount()),
                food.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME),
                food.getLastModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}
