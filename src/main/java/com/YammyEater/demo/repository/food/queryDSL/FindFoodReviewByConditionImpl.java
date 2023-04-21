package com.YammyEater.demo.repository.food.queryDSL;

import com.YammyEater.demo.domain.food.FoodReview;
import com.YammyEater.demo.domain.food.QFoodReview;
import com.YammyEater.demo.domain.user.QUser;
import com.YammyEater.demo.dto.food.FoodReviewConditionalRequest;
import com.YammyEater.demo.dto.food.FoodReviewDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

public class FindFoodReviewByConditionImpl extends QuerydslRepositorySupport implements FindFoodReviewByCondition {

    public FindFoodReviewByConditionImpl() {
        super(FoodReview.class);
    }

    @Override
    @Transactional
    public Page<FoodReviewDto> findFoodReviewPageByCondition(
            Long foodId,
            FoodReviewConditionalRequest foodReviewConditionalRequest,
            Pageable pageable)
    {

        QFoodReview foodReview = QFoodReview.foodReview;
        QUser user = QUser.user;

        //기본 쿼리
        JPQLQuery<FoodReview> query = from(foodReview);

        //조건 추가
        applyCondition(query, foodId, foodReviewConditionalRequest);

        //패치 조인
        query.join(foodReview.user, user).fetchJoin();

        //페이징 적용
        getQuerydsl().applyPagination(pageable, query);

        //쿼리 실행, dto로 변환
        List<FoodReview> queryResult = query.fetch();
        List<FoodReviewDto> dtoResult = queryResult.stream().map(FoodReviewDto::of).toList();

        //count query 생성, 페이징을 위해 개수 가져오기
        JPQLQuery countQuery = from(foodReview);
        applyCondition(countQuery, foodId, foodReviewConditionalRequest);
        Long totalCount = countQuery.fetchCount();

        //PageImpl로 변환해서 반환
        return new PageImpl<FoodReviewDto>(dtoResult, pageable, totalCount);
    }

    private JPQLQuery applyCondition(JPQLQuery query, Long foodId, FoodReviewConditionalRequest foodReviewConditionalRequest) {
        QFoodReview foodReview = QFoodReview.foodReview;

        query.where(eq(foodReview.food.id, foodId));
        query.where(eq(foodReview.user.id, foodReviewConditionalRequest.userId()));
        query.where(eq(foodReview.user.username, foodReviewConditionalRequest.userName()));
        query.where(eq(foodReview.rating, foodReviewConditionalRequest.rating()));

        return query;
    }

    private <T> BooleanExpression eq(SimpleExpression simpleExpression, T targ) {
        if(targ == null){
            return null;
        }
        return simpleExpression.eq(targ);
    }

}
