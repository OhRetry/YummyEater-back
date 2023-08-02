package com.YammyEater.demo.domain.user;

import com.YammyEater.demo.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name="REFRESH_TOKEN_INFO")
@NoArgsConstructor
public class RefreshTokenInfo extends BaseTimeEntity {

    @Id
    @Column(name = "REFRESH_TOKEN")
    @Setter
    String refreshToken;

    @Column(name = "LAST_ACCESS_TOKEN")
    @Setter
    String lastAccessToken;

    @Setter
    @Column(name = "USER_ID")
    Long userId;


    @Builder
    public RefreshTokenInfo(String refreshToken, String lastAccessToken, Long userId) {
        this.refreshToken = refreshToken;
        this.lastAccessToken = lastAccessToken;
        this.userId = userId;
    }
}
