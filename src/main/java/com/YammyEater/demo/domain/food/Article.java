package com.YammyEater.demo.domain.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "ARTICLE")
public class Article {
    @Id @GeneratedValue
    @Column(name = "ARTICLE_ID")
    private Long id;

    @Setter
    @Column(name = "CONTENT", length = 5000)
    private String content;

    @Builder
    public Article(String content) {
        this.content = content;
    }

    public Article() {
    }
}
