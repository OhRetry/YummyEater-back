package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.domain.food.Nutrient;

public record NutrientDto(
        float calorie,
        float carbohydrate,
        float sugars,
        float dietaryFiber,
        float protein,
        float fat,
        float saturatedFat,
        float unsaturatedFat,
        float natrium
) {
    public static NutrientDto of(Nutrient nutrient) {
        return new NutrientDto(
                nutrient.getCalorie(),
                nutrient.getCarbohydrate(),
                nutrient.getSugars(),
                nutrient.getDietaryFiber(),
                nutrient.getProtein(),
                nutrient.getFat(),
                nutrient.getSaturatedFat(),
                nutrient.getUnsaturatedFat(),
                nutrient.getNatrium()
        );
    }

    public Nutrient to() {
        return Nutrient.builder()
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .sugars(sugars)
                .dietaryFiber(dietaryFiber)
                .protein(protein)
                .fat(fat)
                .saturatedFat(saturatedFat)
                .unsaturatedFat(unsaturatedFat)
                .natrium(natrium)
                .build();
    }

    //Nutrient에 값을 대입
    public void substitute(Nutrient nutrient) {
        nutrient.setCalorie(calorie);
        nutrient.setCarbohydrate(carbohydrate);
        nutrient.setSugars(sugars);
        nutrient.setDietaryFiber(dietaryFiber);
        nutrient.setProtein(protein);
        nutrient.setFat(fat);
        nutrient.setSaturatedFat(saturatedFat);
        nutrient.setUnsaturatedFat(unsaturatedFat);
        nutrient.setNatrium(natrium);
    }
}
