package com.YammyEater.demo.dto.food;

import com.YammyEater.demo.domain.food.Tag;

public record TagDto(Long id, String name) {
    public static TagDto of(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }
}
