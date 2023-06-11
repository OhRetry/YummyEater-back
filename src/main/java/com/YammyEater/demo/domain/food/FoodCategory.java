package com.YammyEater.demo.domain.food;

import com.YammyEater.demo.domain.food.FoodCategory.FoodCategoryId;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="FOOD_CATEGORY")
@IdClass(FoodCategoryId.class)
public class FoodCategory {

    @EqualsAndHashCode
    public static class FoodCategoryId implements Serializable {
        private Long food;
        private Long category;

    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FOOD_ID")
    private Food food;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CATEGORY_ID")
    private Category category;
}
