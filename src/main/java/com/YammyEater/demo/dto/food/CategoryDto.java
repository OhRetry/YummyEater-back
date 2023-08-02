package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.domain.food.Category;

public record CategoryDto(Long id, String name) {
    public static CategoryDto of(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
