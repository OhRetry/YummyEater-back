package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record FoodRegisterRequest(
        @NotBlank
        String title,
        FoodType type,
        Integer servings,
        Float amount,
        String ingredient,
        Long price,
        String maker,
        String imgUrl,
        List<String> categories,
        List<String> tags,
        NutrientDto nutrient,
        @NotBlank @Length(max = 5000)
        String content
) {
}
