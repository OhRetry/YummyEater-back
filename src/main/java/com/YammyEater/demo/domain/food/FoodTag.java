package com.YammyEater.demo.domain.food;

import com.YammyEater.demo.domain.BaseTimeEntity;
import com.YammyEater.demo.domain.food.FoodCategory.FoodCategoryId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@NoArgsConstructor
@Getter
@Table(name="FOOD_TAG")
@IdClass(FoodTag.FoodTagId.class)
public class FoodTag extends BaseTimeEntity {
    @EqualsAndHashCode
    public static class FoodTagId implements Serializable {
        private Long food;
        private String tag;

    }

    @Id
    @ManyToOne
    @JoinColumn(name = "FOOD_ID", referencedColumnName = "FOOD_ID")
    Food food;

    @Id
    @Column(name = "TAG")
    String tag;

    public FoodTag(Food food, String tag) {
        this.food = food;
        this.tag = tag;
    }
}
