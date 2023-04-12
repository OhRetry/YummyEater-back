package com.YammyEater.demo.dto.food;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record FoodReviewRegisterRequest(
        @NotNull @Min(1) @Max(5)
        int rating,
        @NotNull
        String content
) {
}
