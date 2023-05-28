package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record FoodModifyRequest(
        String name,
        String title,
        FoodType type,
        String ingredient,
        Long price,
        String maker,
        String imgUrl,
        List<String> tags,
        NutrientDto nutrient,
        @Length(max = 5000)
        String content
) {
}
