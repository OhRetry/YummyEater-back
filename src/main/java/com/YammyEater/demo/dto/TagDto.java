package com.YammyEater.demo.dto;

import com.YammyEater.demo.domain.Tag;

public record TagDto(Long id, String name) {
    static TagDto of(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }
}
