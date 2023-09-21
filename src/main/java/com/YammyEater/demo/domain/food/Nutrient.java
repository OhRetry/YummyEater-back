package com.YammyEater.demo.domain.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="NUTRIENT_ID")
    private Long id;

    @Setter
    @Column(name="CALORIE")
    private Float calorie;

    @Setter
    @Column(name="CARBOHYDRATE")
    private Float carbohydrate;

    @Setter
    @Column(name="SUGARS")
    private Float sugars;

    @Setter
    @Column(name="DIETARY_FIBER")
    private Float dietaryFiber;

    @Setter
    @Column(name="PROTEIN")
    private Float protein;

    @Setter
    @Column(name="FAT")
    private Float fat;

    @Setter
    @Column(name="SATURATED_FAT")
    private Float saturatedFat;

    @Setter
    @Column(name="UNSATURATED_FAT")
    private Float unsaturatedFat;

    @Setter
    @Column(name="NATRIUM")
    private Float natrium;

    @Setter
    @OneToOne(mappedBy = "nutrient", fetch = FetchType.LAZY)
    private Food food;

    @Builder
    public Nutrient(
            Float calorie,
            Float carbohydrate,
            Float sugars,
            Float dietaryFiber,
            Float protein,
            Float fat,
            Float saturatedFat,
            Float unsaturatedFat,
            Float natrium
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
