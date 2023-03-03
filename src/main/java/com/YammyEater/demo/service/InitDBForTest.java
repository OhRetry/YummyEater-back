package com.YammyEater.demo.service;

import com.YammyEater.demo.constant.FoodType;
import com.YammyEater.demo.domain.Article;
import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.domain.FoodReview;
import com.YammyEater.demo.domain.FoodReviewRatingCount;
import com.YammyEater.demo.domain.FoodTag;
import com.YammyEater.demo.domain.Nutrient;
import com.YammyEater.demo.domain.Tag;
import com.YammyEater.demo.domain.User;
import com.YammyEater.demo.repository.ArticleRepository;
import com.YammyEater.demo.repository.FoodRepository;
import com.YammyEater.demo.repository.FoodReviewRatingCountRepository;
import com.YammyEater.demo.repository.FoodReviewRepository;
import com.YammyEater.demo.repository.FoodTagRepository;
import com.YammyEater.demo.repository.NutrientRepository;
import com.YammyEater.demo.repository.TagRepository;
import com.YammyEater.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitDBForTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    FoodReviewRepository foodReviewRepository;
    @Autowired
    FoodReviewRatingCountRepository foodReviewRatingCountRepository;
    @Autowired
    NutrientRepository nutrientRepository;
    @Autowired
    FoodTagRepository foodTagRepository;
    @Autowired
    ArticleRepository articleRepository;

    @PostConstruct
    @Transactional
    public void init() {
        String[] tagNames = {"한식", "일식", "중식", "양식", "고기", "야채", "달콤한", "매운"};
        List<Tag> tags = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag newTag = Tag.builder().name(tagName).build();
            tagRepository.save(newTag);
            tags.add(newTag);
        }

        for (int i = 0; i < 10; i++) {
            User newUser = User.builder()
                    .email("email@" + i)
                    .password("qqq")
                    .username("user" + i)
                    .build();
            userRepository.save(newUser);
            users.add(newUser);
        }

        Random rand = new Random();
        for (int i = 0; i < 150; i++) {
            //유저
            User food_user = users.get(rand.nextInt(users.size()));

            //영양소
            Nutrient food_nutrient = Nutrient.builder()
                    .calorie((float)Math.round(rand.nextFloat() * 10000) / 10)
                    .carbohydrate((float)Math.round(rand.nextFloat() * 500) / 10)
                    .sugars((float)Math.round(rand.nextFloat() * 200) / 10)
                    .protein((float)Math.round(rand.nextFloat() * 200) / 10)
                    .fat((float)Math.round(rand.nextFloat() * 200) / 10)
                    .unsaturatedFat((float)Math.round(rand.nextFloat() * 100) / 10)
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
                            .name("test" + i)
                            .title("test" + i)
                            .rating(0)
                            .type(FoodType.RECIPE)
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


            //태그
            Set<Tag> food_tags = new HashSet<>();
            for (int k = 0; k < 3; k++) {
                food_tags.add(tags.get(rand.nextInt(tags.size())));
            }

            for (Tag food_tag : food_tags) {
                foodTagRepository.save(
                        new FoodTag(newFood, food_tag)
                );
            }

        }

    }
}
