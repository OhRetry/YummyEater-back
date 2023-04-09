package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;
import java.util.List;

public record FoodRegisterRequest(
        String name,
        String title,
        FoodType type,
        String ingredient,
        Long price,
        String maker,
        String imgUrl,
        List<String> tags,
        NutrientDto nutrient,
        String content
) {
}
