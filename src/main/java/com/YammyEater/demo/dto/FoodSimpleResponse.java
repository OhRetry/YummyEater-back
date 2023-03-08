package com.YammyEater.demo.dto;

import com.YammyEater.demo.constant.FoodType;
import com.YammyEater.demo.domain.Food;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record FoodSimpleResponse(
        Long id,
        FoodType type,
        String name,
        String title,

        Long userId,
        String userName,

        String imgUrl,

        float rating,

        Long price,
        String maker,

        List<String> tags,

        String createdAt,
        String lastModifiedAt
) {
    public static FoodSimpleResponse of(Food food) {
        return new FoodSimpleResponse(
                food.getId(),
                food.getType(),
                food.getName(),
                food.getTitle(),
                food.getUser().getId(),
                food.getUser().getUsername(),
                food.getImgUrl(),
                food.getRating(),
                food.getPrice(),
                food.getMaker(),
                food.getTags().stream().map(x -> x.getTag().getName()).toList(),
                food.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME),
                food.getLastModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}
