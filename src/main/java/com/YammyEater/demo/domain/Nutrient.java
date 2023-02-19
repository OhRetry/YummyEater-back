package com.YammyEater.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "NUTRIENT")
public class Nutrient {
    @Id
    @Column(name="FOOD_ID")
    private Long id;

    @Column(name="CALORIE")
    private float calorie;

    @Column(name="CARBOHYDRATE")
    private float carbohydrate;

    @Column(name="SUGARS")
    private float sugars;

    @Column(name="PROTEIN")
    private float protein;

    @Column(name="FAT")
    private float fat;

    @Column(name="UNSATURATED_FAT")
    private float unsaturatedFat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @Builder
    public Nutrient(Long id, float calorie, float carbohydrate, float sugars, float protein, float fat,
                    float unsaturatedFat) {
        this.id = id;
        this.calorie = calorie;
        this.carbohydrate = carbohydrate;
        this.sugars = sugars;
        this.protein = protein;
        this.fat = fat;
        this.unsaturatedFat = unsaturatedFat;
    }

    public Nutrient() {
    }
}
