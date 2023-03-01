package com.YammyEater.demo.repository.queryDSL.FoodRepository;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.domain.QFood;
import com.YammyEater.demo.domain.QFoodTag;
import com.YammyEater.demo.domain.QTag;
import com.YammyEater.demo.domain.QUser;
import com.YammyEater.demo.dto.FoodConditionalRequest;
import com.YammyEater.demo.dto.FoodSimpleResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FoodRepositoryCustomImpl extends QuerydslRepositorySupport implements FoodRepositoryCustom {

    public FoodRepositoryCustomImpl() {
        super(Food.class);
    }

    @Override
    public Page<FoodSimpleResponse> findFoodSimpleResponsePageByCondition(
            FoodConditionalRequest foodConditionalRequest,
            Pageable pageable
    ) {
        QFood food = QFood.food;
        QUser user = QUser.user;
        QFoodTag foodTag = QFoodTag.foodTag;
        QTag tag = QTag.tag;

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
                .join(food.tags, foodTag)
                .fetchJoin()
                .join(foodTag.tag, tag)
                .fetchJoin()
                .where(food.id.in(ids));
        fetchQuery.fetch();


        //결과 객체 리스트를 영속성 캐시에서 가져옴.
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
                    case "id","rating","name","title","price","maker":
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
        if(req.tags() != null) {
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
        applyTagCondition(query, req, QFood.food, QFoodTag.foodTag, QTag.tag);
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

    private <T> JPQLQuery<T> applyTagCondition(
            JPQLQuery<T> query,
            FoodConditionalRequest req,
            QFood food,
            QFoodTag foodTag,
            QTag tag
    ) {
        //tag 조건 검색
        //요청 tag를 모두 만족해야 함. 대략적으로 세가지 방법이 있음
        //1. 조건의 태그를 가진 행만 남겨두고 group by로 개수를 세는 방법
        //2. 계속 foodTag와 tag를 join해가며 조건의 태그와 같은지 검사, 남은 행을 구하는 방법 (검색 태그 개수 * 2만큼 join.)
        //3. not exist + not exist문으로 바꿔서 처리.(검색 태그에 포함되지 않는 행이 존재하지 않는 것만 뽑아냄)
        //1번이 가장 성능이 좋아 보여서 선택했다.
        if(req.tags() != null) {
            //1:N관계 이므로 where절이 들어가면 컬랙션 정합성이 유지되지 않으므로 fetch join은 하지 않음.
            query.join(food.tags, foodTag);
            query.join(foodTag.tag, tag);

            //모든 tag를 갖는다는 것은 조건을 만족하는 엔티티 개수가 태그의 개수인 것.
            query.where(tag.name.in(req.tags()));
            Long tagCnt = Long.valueOf(req.tags().length);

            query.groupBy(food.id).having(food.id.count().eq(tagCnt));

        }
        return query;
    }
}
