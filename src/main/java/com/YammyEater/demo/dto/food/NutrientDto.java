package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.domain.food.Nutrient;

public record NutrientDto(
        float calorie,
        float carbohydrate,
        float sugars,
        float protein,
        float fat,
        float unsaturatedFat
) {
    public static NutrientDto of(Nutrient nutrient) {
        return new NutrientDto(
                nutrient.getId(),
                nutrient.getCarbohydrate(),
                nutrient.getSugars(),
                nutrient.getProtein(),
                nutrient.getFat(),
                nutrient.getUnsaturatedFat()
        );
    }
}
