package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.constant.food.FoodType;

public record FoodConditionalRequest(
        FoodType type,
        String name,
        String title,
        String[] tags,
        Long userId,
        String userName
) {
}