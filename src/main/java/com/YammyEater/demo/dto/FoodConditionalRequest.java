package com.YammyEater.demo.dto;

import com.YammyEater.demo.constant.FoodType;

public record FoodConditionalRequest(
        FoodType type,
        String name,
        String title,
        String[] tags,
        Long userId,
        String userName
) {
}
