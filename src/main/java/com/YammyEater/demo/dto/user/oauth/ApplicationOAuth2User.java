package com.YammyEater.demo.dto.user.oauth;

import com.YammyEater.demo.constant.user.OAuthProvider;
import com.YammyEater.demo.domain.user.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Getter
@Setter
public class ApplicationOAuth2User implements OAuth2User {

    private User user;
    private String email;
    private OAuthProvider oAuthProvider;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public ApplicationOAuth2User(User user, String email, OAuthProvider oAuthProvider, Map<String, Object> attributes) {
        this.user = user;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
        this.attributes = attributes;
        this.authorities = AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return email;
    }
}
