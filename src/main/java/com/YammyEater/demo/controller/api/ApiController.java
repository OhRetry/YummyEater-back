package com.YammyEater.demo.controller.api;

import com.YammyEater.demo.constant.ErrorCode;
import com.YammyEater.demo.constant.FoodType;
import com.YammyEater.demo.domain.Article;
import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.domain.FoodReviewRatingCount;
import com.YammyEater.demo.domain.FoodTag;
import com.YammyEater.demo.domain.Nutrient;
import com.YammyEater.demo.domain.Tag;
import com.YammyEater.demo.domain.User;
import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.FoodConditionalRequest;
import com.YammyEater.demo.dto.FoodDetailResponse;
import com.YammyEater.demo.dto.FoodReviewDto;
import com.YammyEater.demo.dto.FoodSimpleResponse;
import com.YammyEater.demo.dto.TagDto;
import com.YammyEater.demo.service.FoodReviewService;
import com.YammyEater.demo.service.FoodService;
import com.YammyEater.demo.service.TagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    FoodService foodService;

    @Autowired
    TagService tagService;

    @Autowired
    FoodReviewService foodReviewService;

    //ex
    //http://localhost:8080/api/food?type=RECIPE&tags=매운&sort=rating,desc&sort=id,desc&page=1size=5
    //http://localhost:8080/api/food?type=RECIPE&tags=매운&tags=달콤한&sort=rating,desc&page=2size=5
    @GetMapping("api/food")
    public Page<FoodSimpleResponse> getFoodByCondition(FoodConditionalRequest foodConditionalRequest, Pageable pageable) {
        return foodService.findFoodByCondition(foodConditionalRequest, pageable);
    }

    //ex
    //http://localhost:8080/api/food/13
    @GetMapping("api/food/{id}")
    public ApiResponse<FoodDetailResponse> getFoodById(@PathVariable(name = "id") Long id) {
        FoodDetailResponse res = foodService.findFoodById(id);
        if(res == null) {
            return ApiResponse.of(null, ErrorCode.BAD_REQUEST);
        }
        return ApiResponse.of(res);
    }

    //ex
    //http://localhost:8080/api/tag
    @GetMapping("/api/tag")
    public ApiResponse<List<TagDto>> getAllTag() {
        return ApiResponse.of(tagService.getAllTag(), ErrorCode.SUCCESS);
    }

    //ex
    //http://localhost:8080/api/food/3/review?sort=rating,desc&page=2&size=10
    @GetMapping("api/food/{foodId}/review")
    public Page<FoodReviewDto> getFoodReview(@PathVariable(name = "foodId") Long foodId, Pageable pageable) {
        return foodReviewService.getFoodReviewPageByFoodId(foodId, pageable);
    }


}
