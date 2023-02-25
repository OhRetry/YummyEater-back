package com.YammyEater.demo.dto;

import com.YammyEater.demo.constant.FoodType;
import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.domain.FoodTag;
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

        String ingredient,
        Long price,
        String maker,

        String tags
) {
    static FoodSimpleResponse of(Food food) {
        List<FoodTag> tags = food.getTags();
        String tagsToStr = String.join(",", tags.stream().map(x -> x.toString()).toList());

        return new FoodSimpleResponse(
                food.getId(),
                food.getType(),
                food.getName(),
                food.getTitle(),
                food.getUser().getId(),
                food.getUser().getUsername(),
                food.getImgUrl(),
                food.getRating(),
                food.getIngredient(),
                food.getPrice(),
                food.getMaker(),
                tagsToStr
        );
    }
}
