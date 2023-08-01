package com.YammyEater.demo.domain.user;

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
@Table(name="USER")
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Setter
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Setter
    @Column(name = "ACCOUNT_PW")
    private String password;

    @Setter
    @Column(name = "USERNAME", unique = true)
    private String username;

    @Setter
    @Column(name = "OAUTH_PROVIDER_NAME")
    private String oauthProviderName;

    @Builder
    public User(String email, String password, String username, String oauthProviderName) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.oauthProviderName = oauthProviderName;
    }

    public User() {
    }
}
