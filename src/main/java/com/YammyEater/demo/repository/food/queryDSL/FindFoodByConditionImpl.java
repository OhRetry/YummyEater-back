package com.YammyEater.demo.repository.food.queryDSL;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.QCategory;
import com.YammyEater.demo.domain.food.QFood;
import com.YammyEater.demo.domain.food.QFoodCategory;
import com.YammyEater.demo.domain.food.QFoodTag;
import com.YammyEater.demo.domain.user.QUser;
import com.YammyEater.demo.dto.food.FoodConditionalRequest;
import com.YammyEater.demo.dto.food.FoodSimpleResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

public class FindFoodByConditionImpl extends QuerydslRepositorySupport implements FindFoodByCondition {

    public FindFoodByConditionImpl() {
        super(Food.class);
    }

    @Override
    @Transactional
    public Page<FoodSimpleResponse> findFoodSimpleResponsePageByCondition(
            FoodConditionalRequest foodConditionalRequest,
            Pageable pageable
    ) {
        QFood food = QFood.food;
        QUser user = QUser.user;
        QFoodCategory foodCategory = QFoodCategory.foodCategory;
        QFoodTag foodTag = QFoodTag.foodTag;
        QCategory category = QCategory.category;

        //먼저 조건을 만족하는 food의 id를 얻어옴
        JPQLQuery<Long> query = from(food).select(food.id);

        //조건 적용
        applyCondition(query, foodConditionalRequest);
        //쿼리에 페이징 적용
        applyPageable(query, pageable);

        List<Long> ids = query.fetch();


        //위의 query에서 구한 food의 id를 이용해 food와 연관된 엔티티를 가져온다.
        //batchSize를 이용하면 쿼리가 3번 나간다. 그냥 직접 쿼리를 날려 한번에 가져온다.
        JPQLQuery<Food> fetchQuery = from(food)
                .join(food.user, user)
                .fetchJoin()
                .join(food.categories, foodCategory)
                .fetchJoin()
                .join(foodCategory.category, category)
                .fetchJoin()
                .join(food.tags, foodTag)
                .fetchJoin()
                .where(food.id.in(ids));
        fetchQuery.fetch();


        //결과 객체 리스트를 영속성 캐시에서 가져옴.
        //fetchQuery의 결과를 이용하지 않는 이유는 1:N에서 중복된 엔티티가 나타나기 때문.
        //hashSet을 이용하면 정렬이 깨진다. 굳이 orderedSet이나 쿼리에 distinct를 사용하지 않고 영속성에서 가져온다.
        List<Food> result = new ArrayList<>();
        for(Long id : ids) {
            result.add(getEntityManager().find(Food.class, id));
        }

        //dto로 변환
        List<FoodSimpleResponse> res = result.stream().map(FoodSimpleResponse::of).toList();

        Long totalCount = getCountQuery(foodConditionalRequest).fetchFirst();
        return new PageImpl<>(res, pageable, totalCount);
    }
    private <T> JPQLQuery<T> applyPageable(
            JPQLQuery<T> query,
            Pageable pageable
    ){
        //쿼리에 페이징 적용
        query.orderBy(getOrderSpecifiers(pageable).stream().toArray(OrderSpecifier[]::new));
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
        return query;
    }
    private List<OrderSpecifier> getOrderSpecifiers(
            Pageable pageable
    ) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order dir = Order.ASC;
                if(order.getDirection().isDescending()) {
                    dir = Order.DESC;
                }

                String propertyName = order.getProperty();
                switch (propertyName) {
                    case "id","rating","name","title","price","maker","createdAt":
                        orderSpecifiers.add(
                                new OrderSpecifier(
                                        dir,
                                        Expressions.path(Object.class, QFood.food, propertyName)
                                )
                        );
                        break;
                }
            }
        }
        return orderSpecifiers;
    }
    private JPQLQuery<Long> getCountQuery(
            FoodConditionalRequest req
    ) {
        JPQLQuery<Long> subQuery = from(QFood.food).select(QFood.food.id);
        applyCondition(subQuery, req);

        JPQLQuery<Long> countQuery;
        if(req.categories() != null || req.tags() != null) {
            QFood foodc = new QFood("foodc");
            countQuery = from(foodc).select(foodc.id.count()).where(foodc.id.in(subQuery));
        }
        else {
            countQuery = subQuery.select(QFood.food.id.count());
        }
        return countQuery;
    }
    private <T> JPQLQuery<T> applyCondition(
            JPQLQuery<T> query,
            FoodConditionalRequest req
    ){
        applyFoodCondition(query, req, QFood.food);
        applyUserCondition(query, req, QFood.food, QUser.user);
        applyCategoryAndTagCondition(query, req, QFood.food, QFoodCategory.foodCategory, QCategory.category, QFoodTag.foodTag);
        return query;
    }
    private <T> JPQLQuery<T> applyFoodCondition(
            JPQLQuery<T> query,
            FoodConditionalRequest req,
            QFood food
    ) {
        //food 검색
        if(req.type() != null) {
            query.where(food.type.eq(req.type()));
        }
        if(req.name() != null) {
            query.where(food.name.contains(req.name()));
        }
        if(req.title() != null) {
            query.where(food.title.contains(req.title()));
        }
        if(req.ingredients() != null) {
            for(String ingredient : req.ingredients()) {
                query.where(food.ingredient.contains(ingredient));
            }
        }
        return query;
    }

    private <T> JPQLQuery<T> applyUserCondition(
            JPQLQuery<T> query,
            FoodConditionalRequest req,
            QFood food,
            QUser user
    ) {
        //유저 검색
        if(req.userId() != null || req.userName() != null) {
            query.join(food.user, user);
            if (req.userId() != null) {
                query.where(user.id.eq(req.userId()));
            }
            if (req.userName() != null) {
                query.where(user.username.eq(req.userName()));
            }
        }
        return query;
    }

    private <T> JPQLQuery<T> applyCategoryAndTagCondition(
            JPQLQuery<T> query,
            FoodConditionalRequest req,
            QFood food,
            QFoodCategory foodCategory,
            QCategory category,
            QFoodTag foodTag
    ) {
        //category, tag 조건 검색
        //요청 category, tag를 모두 만족해야 함. 대략적으로 세가지 방법이 있음
        //1. 조건의 category, tag를 가진 행만 남겨두고 group by로 개수를 세는 방법(모두 만족 = 개수가 category*tag인 것만 선택)
        //2. 계속 category, tag를 join해가며 조건의 category, tag와 같은지 검사, 남은 행을 구하는 방법 (검색 category 개수 * 2 + 검색 tag 개수만큼 join 필요.)
        //3. not exist + not exist문으로 바꿔서 처리.(검색 category, tag에 포함되지 않는 행이 존재하지 않는 것만 뽑아냄)
        //1번이 가장 성능이 좋아 보여서 선택했다.
        Long targetCnt = 1L;
        if(req.categories() != null) {
            //1:N관계 이므로 where절이 들어가면 컬랙션 정합성이 유지되지 않으므로 fetch join은 하지 않음.
            query.join(food.categories, foodCategory);
            query.join(foodCategory.category, category);

            query.where(category.name.in(req.categories()));
            targetCnt *= Long.valueOf(req.categories().length);
        }
        if(req.tags() != null) {
            query.join(food.tags, foodTag);

            query.where(foodTag.tag.in(req.tags()));
            targetCnt *= Long.valueOf(req.tags().length);
        }
        //모든 category,tag를 갖는다는 것은 조건을 만족하는 행의 개수가 카테고리 개수 * 태그의 개수인 것.
        query.groupBy(food.id).having(food.id.count().eq(targetCnt));
        return query;
    }
}
