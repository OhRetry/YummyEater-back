package com.YammyEater.demo.service.user.oauth;

import com.YammyEater.demo.constant.user.OAuthProvider;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.user.oauth.ApplicationOAuth2User;
import com.YammyEater.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleOAuthUserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        final String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email);
        return new ApplicationOAuth2User(user, email, OAuthProvider.GOOGLE, oAuth2User.getAttributes());
    }
}
