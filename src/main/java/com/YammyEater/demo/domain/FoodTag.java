package com.YammyEater.demo.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="FOOD_TAG")
@IdClass(FoodTag.FoodTagId.class)
public class FoodTag {

    public class FoodTagId implements Serializable {
        private Long food;
        private Long tag;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FOOD_ID")
    private Food food;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TAG_ID")
    private Tag tag;
}
