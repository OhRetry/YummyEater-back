package com.YammyEater.demo.service.food;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReview;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.food.FoodReviewConditionalRequest;
import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.YammyEater.demo.dto.food.FoodReviewModifyRequest;
import com.YammyEater.demo.dto.food.FoodReviewRegisterRequest;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.food.FoodRepository;
import com.YammyEater.demo.repository.food.FoodReviewRatingCountRepository;
import com.YammyEater.demo.repository.food.FoodReviewRepository;
import com.YammyEater.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodReviewServiceImpl implements FoodReviewService {

    private final FoodRepository foodRepository;
    private final FoodReviewRepository foodReviewRepository;
    private final UserRepository userRepository;
    private final FoodReviewRatingCountRepository foodReviewRatingCountRepository;

    public Page<FoodReviewDto> getFoodReviewPageByCondition(
            Long foodId,
            FoodReviewConditionalRequest foodReviewConditionalRequest,
            Pageable pageable
    ) {
        //return foodReviewRepository.findPageEagerByFoodId(foodId, pageable).map(FoodReviewDto::of);
        return foodReviewRepository.findFoodReviewPageByCondition(foodId, foodReviewConditionalRequest, pageable);
    }

    @Override
    @Transactional
    public Long registerFoodReview(Long userId, Long foodId, FoodReviewRegisterRequest foodReviewRegisterRequest) {
        //음식이 존재하지 않는 경우 bad request
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));

        User user = userRepository.getById(userId);

        FoodReview foodReview = FoodReview.builder()
                .user(user)
                .food(food)
                .content(foodReviewRegisterRequest.content())
                .rating(foodReviewRegisterRequest.rating())
                .build();

        foodReviewRepository.save(foodReview);

        FoodReviewRatingCount foodReviewRatingCount = food.getFoodReviewRatingCount();
        foodReviewRatingCount.increaseRatingCount(foodReviewRegisterRequest.rating());
        recalculateRating(food, foodReviewRatingCount);

        return foodReview.getId();
    }

    @Override
    @Transactional
    public void modifyFoodReview(Long userId, Long reviewId, FoodReviewModifyRequest foodReviewModifyRequest) {
        //존재하지 않는 리뷰의 경우 BAD REQUEST
        FoodReview foodReview = foodReviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));

        User user = userRepository.getById(userId);

        //작성자가 아닐 경우 FORBIDDEN
        if(user != foodReview.getUser()) {
            throw new GeneralException(ErrorCode.FORBIDDEN);
        }

        //내용 수정
        if(foodReviewModifyRequest.content() != null) {
            foodReview.setContent(foodReviewModifyRequest.content());
        }

        //별점 수정
        if(foodReviewModifyRequest.rating() != null) {
            //리뷰 통계 정보 수정
            Food food = foodReview.getFood();
            FoodReviewRatingCount foodReviewRatingCount = food.getFoodReviewRatingCount();
            foodReviewRatingCount.decreaseRatingCount(foodReview.getRating());
            foodReviewRatingCount.increaseRatingCount(foodReviewModifyRequest.rating());
            //리뷰 별점 수정
            foodReview.setRating(foodReviewModifyRequest.rating());
            //평균 별점 재계산
            recalculateRating(food, foodReviewRatingCount);
        }
    }

    @Override
    @Transactional
    public void deleteFoodReview(Long userId, Long reviewId) {
        //존재하지 않는 리뷰의 경우 BAD REQUEST
        FoodReview foodReview = foodReviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));

        User user = userRepository.getById(userId);

        //작성자가 아닐 경우 FORBIDDEN
        if(user != foodReview.getUser()) {
            throw new GeneralException(ErrorCode.FORBIDDEN);
        }

        //리뷰 통계 정보 수정
        Food food = foodReview.getFood();

        FoodReviewRatingCount foodReviewRatingCount = food.getFoodReviewRatingCount();
        foodReviewRatingCount.decreaseRatingCount(foodReview.getRating());
        recalculateRating(food, foodReviewRatingCount);

        foodReviewRepository.deleteById(reviewId);

    }

    private void recalculateRating(Food food, FoodReviewRatingCount foodReviewRatingCount) {
        float rating = 0;
        int totalCnt = 0;
        rating += foodReviewRatingCount.getRate1();
        totalCnt += foodReviewRatingCount.getRate1();
        rating += foodReviewRatingCount.getRate2() * 2;
        totalCnt += foodReviewRatingCount.getRate2();
        rating += foodReviewRatingCount.getRate3() * 3;
        totalCnt += foodReviewRatingCount.getRate3();
        rating += foodReviewRatingCount.getRate4() * 4;
        totalCnt += foodReviewRatingCount.getRate4();
        rating += foodReviewRatingCount.getRate5() * 5;
        totalCnt += foodReviewRatingCount.getRate5();
        if(totalCnt == 0) {
            food.setRating(0);
        }
        else {
            food.setRating(rating / totalCnt);
        }
    }
}
