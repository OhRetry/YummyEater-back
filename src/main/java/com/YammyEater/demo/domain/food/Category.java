package com.YammyEater.demo.domain.food;

import com.YammyEater.demo.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "CATEGORY")
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CATEGORY_ID")
    private Long id;

    @Setter
    @Column(name="NAME")
    private String name;

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }
}
