package com.YammyEater.demo.service.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import com.YammyEater.demo.exception.food.FoodNotExistException;
import com.YammyEater.demo.repository.food.FoodRepository;
import com.YammyEater.demo.repository.food.FoodReviewRatingCountRepository;
import org.springframework.transaction.annotation.Transactional;

public class FoodRatingChanger {
    private Long foodId;
    private Long rate1Diff = 0L;
    private Long rate2Diff = 0L;
    private Long rate3Diff = 0L;
    private Long rate4Diff = 0L;
    private Long rate5Diff = 0L;

    private FoodRepository foodRepository;
    private FoodReviewRatingCountRepository foodReviewRatingCountRepository;
    public FoodRatingChanger(Food food, FoodRepository foodRepository, FoodReviewRatingCountRepository foodReviewRatingCountRepository) {
        foodId = food.getId();
        this.foodRepository = foodRepository;
        this.foodReviewRatingCountRepository = foodReviewRatingCountRepository;
    }

    @Transactional
    public void execute() {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new FoodNotExistException());
        foodReviewRatingCountRepository.addRate(
                food.getFoodReviewRatingCount(),
                rate1Diff,
                rate2Diff,
                rate3Diff,
                rate4Diff,
                rate5Diff
        );
        food = foodRepository.findById(foodId).orElseThrow(() -> new FoodNotExistException());
        recalculateRating(food, food.getFoodReviewRatingCount());
    }

    public void increaseRating(int rating) {
        addRating(rating, 1);
    }

    public void decreaseRating(int rating) {
        addRating(rating, -1);
    }

    public void addRating(int rating, int amount) {
        switch (rating) {
            case 1:
                rate1Diff += amount;
                break;
            case 2:
                rate2Diff += amount;
                break;
            case 3:
                rate3Diff += amount;
                break;
            case 4:
                rate4Diff += amount;
                break;
            case 5:
                rate5Diff += amount;
                break;
        }
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
