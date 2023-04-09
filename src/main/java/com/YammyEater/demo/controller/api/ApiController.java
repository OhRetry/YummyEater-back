package com.YammyEater.demo.controller.api;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.DuplicateCheckResponse;
import com.YammyEater.demo.dto.food.FoodRegisterRequest;
import com.YammyEater.demo.dto.food.FoodRegisterResponse;
import com.YammyEater.demo.dto.user.EmailVerifyingRequest;
import com.YammyEater.demo.dto.user.EmailVerifyingResponse;
import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodDetailResponse;
import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import com.YammyEater.demo.dto.user.SendEmailVerifyingRequest;
import com.YammyEater.demo.dto.user.SignInRequest;
import com.YammyEater.demo.dto.user.SignInResponse;
import com.YammyEater.demo.dto.food.TagDto;
import com.YammyEater.demo.dto.user.UserJoinRequest;
import com.YammyEater.demo.service.food.FoodReviewService;
import com.YammyEater.demo.service.food.FoodService;
import com.YammyEater.demo.service.food.TagService;
import com.YammyEater.demo.service.user.UserJoinService;
import com.YammyEater.demo.service.user.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    FoodService foodService;

    @Autowired
    TagService tagService;

    @Autowired
    FoodReviewService foodReviewService;

    @Autowired
    UserJoinService userJoinService;

    @Autowired
    UserService userService;

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

    @PostMapping("api/food")
    public ApiResponse<FoodRegisterResponse> registerFood(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid FoodRegisterRequest foodRegisterRequest
    ) {
        Long foodId = foodService.registerFood(userId, foodRegisterRequest);
        return ApiResponse.of(new FoodRegisterResponse(foodId));
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


    @PostMapping("/api/user/join/sendVerificationEmail")
    public ApiResponse sendVerificationEmail(@RequestBody @Valid SendEmailVerifyingRequest sendEmailVerifyingRequest) {
        userJoinService.sendVerifyingEmail(sendEmailVerifyingRequest.email());
        return ApiResponse.of(null);
    }

    @GetMapping("/api/user/join/isDuplicateEmail")
    public ApiResponse<DuplicateCheckResponse> checkDuplicatedEmail(@RequestParam("email") String email) {
        boolean duplicated = userService.existsByEmail(email);
        return ApiResponse.of(new DuplicateCheckResponse(duplicated));
    }

    @GetMapping("/api/user/join/isDuplicateUserName")
    public ApiResponse<DuplicateCheckResponse> checkDuplicatedUsername(@RequestParam("userName") String username) {
        boolean duplicated = userService.existsByUsername(username);
        return ApiResponse.of(new DuplicateCheckResponse(duplicated));
    }

    @PostMapping("/api/user/join/verifyEmail")
    public ApiResponse<EmailVerifyingResponse> verifyEmail(@RequestBody @Valid EmailVerifyingRequest emailVerifyingRequest) {
        boolean success = userJoinService.verifyEmailByCode(emailVerifyingRequest.code());
        return ApiResponse.of(new EmailVerifyingResponse(success));
    }

    @PostMapping("/api/user/join")
    public ApiResponse<Object> join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        userJoinService.joinByCode(
                userJoinRequest.code(),
                userJoinRequest.email(),
                userJoinRequest.username(),
                userJoinRequest.password()
        );
        return ApiResponse.of(null);
    }

    @PostMapping("/api/user/signIn")
    public ApiResponse<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        String token = userService.authenticate(signInRequest.email(), signInRequest.password());
        return ApiResponse.of(new SignInResponse(token));
    }

}
