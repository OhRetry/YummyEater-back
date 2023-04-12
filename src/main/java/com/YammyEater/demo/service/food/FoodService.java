package com.YammyEater.demo.service.food;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.domain.food.Article;
import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import com.YammyEater.demo.domain.food.FoodTag;
import com.YammyEater.demo.domain.food.Nutrient;
import com.YammyEater.demo.domain.food.Tag;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodDetailResponse;
import com.YammyEater.demo.dto.food.FoodRegisterRequest;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.food.ArticleRepository;
import com.YammyEater.demo.repository.food.FoodRepository;
import com.YammyEater.demo.repository.food.FoodReviewRatingCountRepository;
import com.YammyEater.demo.repository.food.FoodTagRepository;
import com.YammyEater.demo.repository.food.NutrientRepository;
import com.YammyEater.demo.repository.food.TagRepository;
import com.YammyEater.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodService {
    @Autowired
    FoodRepository foodRepository;

    @Autowired
    NutrientRepository nutrientRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FoodReviewRatingCountRepository foodReviewRatingCountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    FoodTagRepository foodTagRepository;

    public Page<FoodSimpleResponse> findFoodByCondition(FoodConditionalRequest foodConditionalRequest, Pageable pageable) {
        return foodRepository.findFoodSimpleResponsePageByCondition(foodConditionalRequest, pageable);
    }

    @Transactional(readOnly = true)
    public FoodDetailResponse findFoodById(Long id) {
        FoodDetailResponse res = foodRepository.findEagerById(id).map(FoodDetailResponse::of).orElse(null);
        return res;
    }

    @Transactional
    public Long registerFood(Long userId, FoodRegisterRequest foodRegisterRequest) {
        //유저 id로 가져옴
        User user = userRepository.getById(userId);

        //영양정보 생성
        Nutrient nutrient = foodRegisterRequest.nutrient().to();
        nutrientRepository.save(nutrient);

        //기사 본문 생성
        Article article = Article.builder()
                .content(foodRegisterRequest.content())
                .build();
        articleRepository.save(article);

        //리뷰 정보 통계 생성
        FoodReviewRatingCount foodReviewRatingCount = FoodReviewRatingCount.builder()
                .rate1(0L)
                .rate2(0L)
                .rate3(0L)
                .rate4(0L)
                .rate5(0L)
                .build();
        foodReviewRatingCountRepository.save(foodReviewRatingCount);

        //음식 생성
        Food food = Food.builder()
                .name(foodRegisterRequest.name())
                .title(foodRegisterRequest.title())
                .rating(0)
                .type(foodRegisterRequest.type())
                .ingredient(foodRegisterRequest.ingredient())
                .price(foodRegisterRequest.price())
                .maker(foodRegisterRequest.maker())
                .imgUrl(foodRegisterRequest.imgUrl())
                .user(user)
                .nutrient(nutrient)
                .article(article)
                .foodReviewRatingCount(foodReviewRatingCount)
                .build();
        foodRepository.save(food);

        //태그 설정
        for(String tagName : foodRegisterRequest.tags()) {
            Tag tag = tagRepository.findByName(tagName);
            //존재하지 않는 태그로 등록 요청한 경우
            if(tag == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST);
            }
            foodTagRepository.save(
                    new FoodTag(food, tag)
            );
        }

        //등록한 음식의 id 반환
        return food.getId();
    }
}
