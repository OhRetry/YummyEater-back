package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;
import java.util.List;
import javax.validation.constraints.NotBlank;

public record FoodRegisterRequest(
        @NotBlank
        String name,
        @NotBlank
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
