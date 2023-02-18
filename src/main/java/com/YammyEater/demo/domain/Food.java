package com.YammyEater.demo.domain;

import com.YammyEater.demo.constant.FoodType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="FOOD")
@Getter
@Setter
public class Food {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "FOOD_ID")
    private Long id;

    //음식 이름
    @Column(name = "NAME")
    private String name;

    //소개글 제목
    @Column(name = "TITLE")
    private String title;

    //리뷰 평균 평점
    @Column(name = "RATING")
    private float rating;

    //음식 종류(레시피 = RECIPE, 완제품 = PRODUCT, 배달 = DELIVERY)
    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private FoodType type;

    //음식 재료 설명(닭, 배추 등)
    @Column(name = "INGREDIENT")
    private String ingredient;

    //가격
    @Column(name = "PRICE")
    private Long price;

    //회사(배달음식이나 완제품의 경우 회사명 필요)
    @Column(name = "MAKER")
    private String maker;

    //대표이미지 경로
    @Column(name = "IMG_URL")
    private String imgUrl;

    //영양소
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private Nutrient nutrient;

    //연결된 태그들
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private List<FoodTag> tags = new ArrayList<>();

    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private Article article;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private FoodReviewRatingCount foodReviewRatingCount;
}
