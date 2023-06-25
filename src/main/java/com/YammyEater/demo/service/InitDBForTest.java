package com.YammyEater.demo.service;

import com.YammyEater.demo.constant.food.FoodType;
import com.YammyEater.demo.domain.food.Article;
import com.YammyEater.demo.domain.food.Category;
import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodCategory;
import com.YammyEater.demo.domain.food.FoodReview;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import com.YammyEater.demo.domain.food.FoodTag;
import com.YammyEater.demo.domain.food.Nutrient;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.repository.food.ArticleRepository;
import com.YammyEater.demo.repository.food.FoodRepository;
import com.YammyEater.demo.repository.food.FoodReviewRatingCountRepository;
import com.YammyEater.demo.repository.food.FoodReviewRepository;
import com.YammyEater.demo.repository.food.FoodCategoryRepository;
import com.YammyEater.demo.repository.food.FoodTagRepository;
import com.YammyEater.demo.repository.food.NutrientRepository;
import com.YammyEater.demo.repository.food.CategoryRepository;
import com.YammyEater.demo.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitDBForTest {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;
    private final FoodReviewRepository foodReviewRepository;
    private final FoodReviewRatingCountRepository foodReviewRatingCountRepository;
    private final NutrientRepository nutrientRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final FoodTagRepository foodTagRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;

    private final String[] categoryNames = {
            "밥", "죽/스프", "면", "떡", "빵", "디저트", "국", "찌개/전골", "샐러드", "반찬", "양념장",
            "조림", "볶음", "구이", "오븐", "튀김", "부침", "찜",
            "술안주", "간편식", "다이어트", "보양식", "채식", "명절",
            "한식", "양식", "일식", "중식", "동남아식", "퓨전식",
            "육류", "소고기", "돼지고기", "닭고기", "오리고기", "해산물", "어류", "패류/갑각류", "해조류", "알/유제품", "채소", "버섯", "콩/견과류"
    };
    private List<Category> categories = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    @PostConstruct
    @Transactional
    public void init() {
        initCategory();
        initUser();
        initFood();
    }
    private void initCategory() {
        for (String categoryName : categoryNames) {
            Category newCategory = Category.builder().name(categoryName).build();
            categoryRepository.save(newCategory);
            categories.add(newCategory);
        }
    }

    private void initUser() {
        for (int i = 0; i < 10; i++) {
            User newUser = User.builder()
                    .email("email" + i + "@test.com")
                    .password(passwordEncoder.encode("1111"))
                    .username("user" + i)
                    .build();
            userRepository.save(newUser);
            users.add(newUser);
        }
    }

    private void initFood() {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            //유저
            User food_user = users.get(rand.nextInt(users.size()));

            //영양소
            Nutrient food_nutrient = Nutrient.builder()
                    .calorie((float)Math.round(rand.nextFloat() * 10000) / 10)
                    .carbohydrate((float)Math.round(rand.nextFloat() * 500) / 10)
                    .sugars((float)Math.round(rand.nextFloat() * 200) / 10)
                    .dietaryFiber((float)Math.round(rand.nextFloat() * 200) / 10)
                    .protein((float)Math.round(rand.nextFloat() * 200) / 10)
                    .fat((float)Math.round(rand.nextFloat() * 200) / 10)
                    .saturatedFat((float)Math.round(rand.nextFloat() * 200) / 10)
                    .unsaturatedFat((float)Math.round(rand.nextFloat() * 100) / 10)
                    .natrium((float)Math.round(rand.nextFloat() * 200) / 10)
                    .build();
            nutrientRepository.save(
                    food_nutrient
            );

            //기사
            Article food_article = Article.builder().content("테스트 컨탠츠 입니다!!!").build();
            articleRepository.save(food_article);


            FoodReviewRatingCount foodReviewRatingCount = FoodReviewRatingCount.builder()
                    .rate1(0L)
                    .rate2(0L)
                    .rate3(0L)
                    .rate4(0L)
                    .rate5(0L)
                    .build();
            foodReviewRatingCountRepository.save(foodReviewRatingCount);


            //음식
            Food newFood = foodRepository.save(
                    Food.builder()
                            .title("test" + i)
                            .rating(0)
                            .type(FoodType.RECIPE)
                            .servings(rand.nextInt(4) + 1)
                            .amount(rand.nextFloat() * 10000)
                            .ingredient("양파, 마늘")
                            .price(Long.valueOf(Math.round(rand.nextFloat() * 100000)))
                            .maker("osm")
                            .imgUrl("test.jpg")
                            .user(food_user)
                            .nutrient(food_nutrient)
                            .article(food_article)
                            .foodReviewRatingCount(foodReviewRatingCount)
                            .build()
            );

            //리뷰
            Long cnts[] = {0L, 0L, 0L, 0L, 0L};
            String reviewString[] = {"최악", "맛없어요", "그냥 그래요", "나름 괜찮아요", "최고에요"};
            for(int k=1;k<=5;k++) {
                int cnt = rand.nextInt(20);

                for(int j=0;j<cnt;j++) {
                    foodReviewRepository.save(
                            FoodReview.builder()
                                    .rating(k)
                                    .content(reviewString[k - 1])
                                    .user(users.get(rand.nextInt(users.size())))
                                    .food(newFood)
                                    .build()
                    );
                    cnts[k - 1] += 1;
                }
            }

            ;
            foodReviewRatingCount.setRate1(cnts[0]);
            foodReviewRatingCount.setRate2(cnts[1]);
            foodReviewRatingCount.setRate3(cnts[2]);
            foodReviewRatingCount.setRate4(cnts[3]);
            foodReviewRatingCount.setRate5(cnts[4]);
            float foodRating = 0;
            Long totalRatingCnt = 0L;
            for(int k=1;k<=5;k++) {
                foodRating += cnts[k - 1] * k;
                totalRatingCnt += cnts[k - 1];
            }
            if(totalRatingCnt > 0) {
                foodRating /= totalRatingCnt;
            }

            newFood.setRating(foodRating);

            foodReviewRatingCountRepository.save(foodReviewRatingCount);
            foodRepository.save(newFood);


            //카테고리
            Set<Category> food_categories = new HashSet<>();
            for (int k = 0; k < 3; k++) {
                food_categories.add(categories.get(rand.nextInt(categories.size())));
            }

            for (Category food_category : food_categories) {
                foodCategoryRepository.save(
                        new FoodCategory(newFood, food_category)
                );
            }

            //태그
            String[] testTags = {"테스트태그1", "테스트태그2", "테스트태그3"};
            for(String tag:testTags) {
                foodTagRepository.save(
                        new FoodTag(newFood, tag)
                );
            }

        }
    }
}
