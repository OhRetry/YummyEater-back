package com.YammyEater.demo.dto.food;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record FoodReviewModifyRequest(
        @Min(1) @Max(5)
        Integer rating,
        @Length(max = 1000)
        String content
) {
}
