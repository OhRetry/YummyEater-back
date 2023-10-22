package com.YammyEater.demo.controller.api.food;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.constant.food.FoodRankingPeriod;
import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.food.*;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.food.FoodRankingRepository;
import com.YammyEater.demo.service.food.FoodRankingService;
import com.YammyEater.demo.service.food.FoodReviewService;
import com.YammyEater.demo.service.food.FoodService;
import com.YammyEater.demo.service.food.CategoryService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final CategoryService categoryService;
    private final FoodReviewService foodReviewService;
    private final FoodRankingService foodRankingService;
    private final FoodRankingRepository foodRankingRepository;

    //ex
    //http://localhost:8080/api/food?type=RECIPE&categories=매운&sort=rating,desc&sort=id,desc&page=1size=5
    //http://localhost:8080/api/food?type=RECIPE&categories=매운&categories=달콤한&sort=rating,desc&page=2size=5
    @GetMapping("api/food")
    public Page<FoodSimpleResponse> getFoodByCondition(FoodConditionalRequest foodConditionalRequest, Pageable pageable) {
        return foodService.findFoodByCondition(foodConditionalRequest, pageable);
    }

    @PostMapping("api/food")
    public ApiResponse<FoodRegisterResponse> registerFood(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid FoodRegisterRequest foodRegisterRequest
    ) {
        Long foodId = foodService.registerFood(userId, foodRegisterRequest);
        return ApiResponse.of(new FoodRegisterResponse(foodId));
    }

    //ex
    //http://localhost:8080/api/food/13
    @GetMapping("api/food/{id}")
    public ApiResponse<FoodDetailResponse> getFoodById(@PathVariable(name = "id") Long id) {
        FoodDetailResponse res = foodService.findFoodById(id);
        //인기 게시물 조회수 추가
        foodRankingRepository.increaseFoodViews(res.id(), res.type());
        return ApiResponse.of(res);
    }

    @PatchMapping("api/food/{id}")
    public ApiResponse<Object> modifyFoodById(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "id") Long foodId,
            @RequestBody @Valid FoodModifyRequest foodModifyRequest
            ) {
        foodService.modifyFood(userId, foodId, foodModifyRequest);
        return ApiResponse.of(null);
    }

    @DeleteMapping("api/food/{id}")
    public ApiResponse<Object> deleteFoodById(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "id") Long foodId
    ) {
        foodService.deleteFood(userId, foodId);
        return ApiResponse.of(null);
    }
    //ex
    //http://localhost:8080/api/category
    @GetMapping("/api/category")
    public ApiResponse<List<CategoryDto>> getAllCategory() {
        return ApiResponse.of(categoryService.getAllCategory(), ErrorCode.SUCCESS);
    }

    //ex
    //http://localhost:8080/api/food/3/review?sort=rating,desc&page=2&size=10
    @GetMapping("api/food/{foodId}/review")
    public Page<FoodReviewDto> getFoodReviewByFoodIdAndCondition(
            @PathVariable(name = "foodId") Long foodId,
            FoodReviewConditionalRequest foodReviewConditionalRequest,
            Pageable pageable
    ) {
        return foodReviewService.getFoodReviewPageByCondition(foodId, foodReviewConditionalRequest, pageable);
    }

    @GetMapping("api/food/review")
    public Page<FoodReviewDto> getFoodReviewByCondition(
            FoodReviewConditionalRequest foodReviewConditionalRequest,
            Pageable pageable
    ) {
        return foodReviewService.getFoodReviewPageByCondition(null, foodReviewConditionalRequest, pageable);
    }


    @PostMapping("api/food/{foodId}/review")
    public ApiResponse<FoodReviewRegisterResponse> registerFoodReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "foodId") Long foodId,
            @RequestBody @Valid FoodReviewRegisterRequest foodReviewRegisterRequest
    ) {
        Long reviewId = foodReviewService.registerFoodReview(userId, foodId, foodReviewRegisterRequest);
        return ApiResponse.of(new FoodReviewRegisterResponse(reviewId));
    }

    @PatchMapping("api/food/review/{reviewId}")
    public ApiResponse<FoodReviewModifyResponse> modifyFoodReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "reviewId") Long reviewId,
            @RequestBody @Valid FoodReviewModifyRequest foodReviewModifyRequest
    ) {
        foodReviewService.modifyFoodReview(userId, reviewId, foodReviewModifyRequest);
        return ApiResponse.of(new FoodReviewModifyResponse(reviewId));
    }

    @DeleteMapping("api/food/review/{reviewId}")
    public ApiResponse<Object> deleteFoodReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "reviewId") Long reviewId
    ) {
        foodReviewService.deleteFoodReview(userId, reviewId);
        return ApiResponse.of(null);
    }

    @GetMapping("/api/food/mostViewed")
    public ApiResponse<List<FoodRankResponse>> getFoodRank(
            FoodRankingPeriod period,
            FoodType type
    ) {
        if(period == null || type == null) {
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        }
        return ApiResponse.of(foodRankingService.getFoodRanking(period, type, 10));
    }
}
