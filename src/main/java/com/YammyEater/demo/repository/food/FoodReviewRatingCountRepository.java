package com.YammyEater.demo.repository.food;

import com.YammyEater.demo.domain.food.Food;
import com.YammyEater.demo.domain.food.FoodReviewRatingCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodReviewRatingCountRepository  extends JpaRepository<FoodReviewRatingCount, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            "update from FoodReviewRatingCount frrc " +
                    "set frrc.rate1 = frrc.rate1 + :r1Diff, " +
                    "frrc.rate2 = frrc.rate2 + :r2Diff, " +
                    "frrc.rate3 = frrc.rate3 + :r3Diff, " +
                    "frrc.rate4 = frrc.rate4 + :r4Diff, " +
                    "frrc.rate5 = frrc.rate5 + :r5Diff " +
            "where frrc=:frrc"
    )
    void addRate(
            @Param("frrc") FoodReviewRatingCount foodReviewRatingCount,
            @Param("r1Diff") Long r1Diff,
            @Param("r2Diff") Long r2Diff,
            @Param("r3Diff") Long r3Diff,
            @Param("r4Diff") Long r4Diff,
            @Param("r5Diff") Long r5Diff
    );
}
