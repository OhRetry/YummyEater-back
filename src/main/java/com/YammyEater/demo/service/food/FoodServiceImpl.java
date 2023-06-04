package com.YammyEater.demo.service.food;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.domain.food.Article;
import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import com.YammyEater.demo.domain.food.FoodCategory;
import com.YammyEater.demo.domain.food.Nutrient;
import com.YammyEater.demo.domain.food.Category;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodDetailResponse;
import com.YammyEater.demo.dto.food.FoodModifyRequest;
import com.YammyEater.demo.dto.food.FoodRegisterRequest;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.food.ArticleRepository;
import com.YammyEater.demo.repository.food.FoodRepository;
import com.YammyEater.demo.repository.food.FoodReviewRatingCountRepository;
import com.YammyEater.demo.repository.food.FoodReviewRepository;
import com.YammyEater.demo.repository.food.FoodCategoryRepository;
import com.YammyEater.demo.repository.food.NutrientRepository;
import com.YammyEater.demo.repository.food.CategoryRepository;
import com.YammyEater.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final NutrientRepository nutrientRepository;
    private final ArticleRepository articleRepository;
    private final FoodReviewRepository foodReviewRepository;
    private final FoodReviewRatingCountRepository foodReviewRatingCountRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public Page<FoodSimpleResponse> findFoodByCondition(FoodConditionalRequest foodConditionalRequest, Pageable pageable) {
        return foodRepository.findFoodSimpleResponsePageByCondition(foodConditionalRequest, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodDetailResponse findFoodById(Long id) {
        FoodDetailResponse res = foodRepository.findEagerById(id).map(FoodDetailResponse::of).orElse(null);
        return res;
    }

    @Override
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
                .servings(foodRegisterRequest.servings())
                .amount(foodRegisterRequest.amount())
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

        //카테고리 설정
        for(String categoryName : foodRegisterRequest.categories()) {
            Category category = categoryRepository.findByName(categoryName);
            //존재하지 않는 카테고리로 등록 요청한 경우
            if(category == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST);
            }
            foodCategoryRepository.save(
                    new FoodCategory(food, category)
            );
        }

        //등록한 음식의 id 반환
        return food.getId();
    }

    @Override
    @Transactional
    public void deleteFood(Long userId, Long foodId) {
        //음식이 존재하지 않을 경우 BAD REQUEST
        Food food = foodRepository
                .findById(foodId)
                .orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));
        //유저가 존재하지 않을 경우는 일어나서는 안됨.(security에서 확인해서 userId를 넘기기 때문에)
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.FORBIDDEN));

        //작성자가 아닐 경우 FORBIDDEN
        if(user != food.getUser()) {
            throw new GeneralException(ErrorCode.FORBIDDEN);
        }


        //Article 삭제
        articleRepository.delete(food.getArticle());

        //영양소 삭제
        nutrientRepository.delete(food.getNutrient());

        //리뷰 삭제
        foodReviewRepository.deleteAllByFood(food);

        //리뷰 통계 삭제
        foodReviewRatingCountRepository.delete(food.getFoodReviewRatingCount());

        //태그 정보 삭제
        foodCategoryRepository.deleteAllByFood(food);

        //food 삭제
        foodRepository.delete(food);

    }

    @Override
    @Transactional
    public void modifyFood(Long userId, Long foodId, FoodModifyRequest foodModifyRequest) {
        //수정하려는 음식이 존재하지 않는 경우 BAD REQUEST
        Food food = foodRepository.findEagerById(foodId).orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));

        //수정하려는 음식의 작성자가 본인이 아닐 경우 FORBIDDEN
        if(userId != food.getUser().getId()) {
            throw new GeneralException(ErrorCode.FORBIDDEN);
        }

        if(foodModifyRequest.name() != null) {
            food.setName(foodModifyRequest.name());
        }
        if(foodModifyRequest.title() != null) {
            food.setTitle(foodModifyRequest.title());
        }
        if(foodModifyRequest.type() != null) {
            food.setType(foodModifyRequest.type());
        }
        if(foodModifyRequest.servings() != null) {
            food.setServings(foodModifyRequest.servings());
        }
        if(foodModifyRequest.amount() != null) {
            food.setAmount(foodModifyRequest.amount());
        }
        if(foodModifyRequest.ingredient() != null) {
            food.setIngredient(foodModifyRequest.ingredient());
        }
        if(foodModifyRequest.price() != null) {
            food.setPrice(foodModifyRequest.price());
        }
        if(foodModifyRequest.maker() != null) {
            food.setMaker(foodModifyRequest.maker());
        }
        if(foodModifyRequest.imgUrl() != null) {
            food.setImgUrl(foodModifyRequest.imgUrl());
        }
        if(foodModifyRequest.categories() != null) {
            //태그 삭제
            foodCategoryRepository.deleteAllByFood(food);
            //태그 설정
            for(String categoryName : foodModifyRequest.categories()) {
                Category category = categoryRepository.findByName(categoryName);
                //존재하지 않는 태그로 등록 요청한 경우
                if(category == null) {
                    throw new GeneralException(ErrorCode.BAD_REQUEST);
                }
                foodCategoryRepository.save(
                        new FoodCategory(food, category)
                );
            }
        }
        if(foodModifyRequest.nutrient() != null) {
            foodModifyRequest.nutrient().substitute(food.getNutrient());
        }
        if(foodModifyRequest.content() != null) {
            food.getArticle().setContent(foodModifyRequest.content());
        }
    }

}
