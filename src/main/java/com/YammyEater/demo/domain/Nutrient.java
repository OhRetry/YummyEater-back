package com.YammyEater.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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

}
