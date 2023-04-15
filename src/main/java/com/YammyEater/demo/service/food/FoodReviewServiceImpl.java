package com.YammyEater.demo.service.food;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReview;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.YammyEater.demo.dto.food.FoodReviewRegisterRequest;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.food.FoodRepository;
import com.YammyEater.demo.repository.food.FoodReviewRatingCountRepository;
import com.YammyEater.demo.repository.food.FoodReviewRepository;
import com.YammyEater.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodReviewServiceImpl implements FoodReviewService {
    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodReviewRepository foodReviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FoodReviewRatingCountRepository foodReviewRatingCountRepository;

    @Override
    public Page<FoodReviewDto> getFoodReviewPageByFoodId(Long foodId, Pageable pageable) {
        return foodReviewRepository.findPageEagerByFoodId(foodId, pageable).map(FoodReviewDto::of);
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


        return foodReview.getId();
    }
}
