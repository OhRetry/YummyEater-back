package com.YammyEater.demo.dto.food;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record FoodReviewRegisterRequest(
        @NotNull @Min(1) @Max(5)
        int rating,
        @NotNull @Length(max = 1000)
        String content
) {
}
