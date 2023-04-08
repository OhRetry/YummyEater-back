package com.YammyEater.demo.dto.food;

import java.util.List;

public record FoodRegisterRequest(
        String name,
        String title,
        String type,
        String ingredient,
        Long price,
        String maker,
        String imgUrl,
        List<String> tags,
        NutrientDto nutrient,
        String content
) {
}
