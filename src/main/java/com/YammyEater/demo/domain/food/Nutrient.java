package com.YammyEater.demo.domain.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "NUTRIENT")
public class Nutrient {
    @Id @GeneratedValue
    @Column(name="NUTRIENT_ID")
    private Long id;

    @Setter
    @Column(name="CALORIE")
    private float calorie;

    @Setter
    @Column(name="CARBOHYDRATE")
    private float carbohydrate;

    @Setter
    @Column(name="SUGARS")
    private float sugars;

    @Setter
    @Column(name="DIETARY_FIBER")
    private float dietaryFiber;

    @Setter
    @Column(name="PROTEIN")
    private float protein;

    @Setter
    @Column(name="FAT")
    private float fat;

    @Setter
    @Column(name="SATURATED_FAT")
    private float saturatedFat;

    @Setter
    @Column(name="UNSATURATED_FAT")
    private float unsaturatedFat;

    @Setter
    @Column(name="NATRIUM")
    private float natrium;

    @Setter
    @OneToOne(mappedBy = "nutrient", fetch = FetchType.LAZY)
    private Food food;

    @Builder
    public Nutrient(
            float calorie,
            float carbohydrate,
            float sugars,
            float dietaryFiber,
            float protein,
            float fat,
            float saturatedFat,
            float unsaturatedFat,
            float natrium
    ) {
        this.calorie = calorie;
        this.carbohydrate = carbohydrate;
        this.sugars = sugars;
        this.dietaryFiber = dietaryFiber;
        this.protein = protein;
        this.fat = fat;
        this.saturatedFat = saturatedFat;
        this.unsaturatedFat = unsaturatedFat;
        this.natrium = natrium;
    }

    public Nutrient() {
    }
}
