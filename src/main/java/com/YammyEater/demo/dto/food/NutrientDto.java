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

    public Nutrient to() {
        return Nutrient.builder()
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .fat(fat)
                .protein(protein)
                .sugars(sugars)
                .unsaturatedFat(unsaturatedFat)
                .build();
    }

    //Nutrient에 값을 대입
    public void substitute(Nutrient nutrient) {
        nutrient.setCalorie(calorie);
        nutrient.setCarbohydrate(carbohydrate);
        nutrient.setFat(fat);
        nutrient.setProtein(protein);
        nutrient.setSugars(sugars);
        nutrient.setUnsaturatedFat(unsaturatedFat);
    }
}
