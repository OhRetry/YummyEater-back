package com.YammyEater.demo.service.user.oauth;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.constant.user.OAuthProvider;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.user.oauth.ApplicationOAuth2User;
import com.YammyEater.demo.dto.user.oauth.OAuthJoinTokenSubject;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.user.UserRepository;
import com.YammyEater.demo.service.user.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.front_success_redirect_url}")
    private String FRONT_SUCCESS_REDIRECT_URL;
    @Value("${spring.security.oauth2.client.front_join_redirect_url}")
    private String FRONT_JOIN_REDIRECT_URL;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        ApplicationOAuth2User applicationOAuth2User = (ApplicationOAuth2User) authentication.getPrincipal();
        User user = applicationOAuth2User.getUser();
        String redirectURL = null;
        String email = applicationOAuth2User.getEmail();
        OAuthProvider oAuthProvider = applicationOAuth2User.getOAuthProvider();

        //user가 존재하지 않는다면 joinToken과 함께 회원가입 페이지로 리다이렉트
        if(user == null) {
            String joinToken = jwtTokenProvider.createOAuthJoinToken(
                    new OAuthJoinTokenSubject(
                        email,
                        oAuthProvider
                    )
            );
            redirectURL = getFullJoinRedirectURL(FRONT_JOIN_REDIRECT_URL, email, joinToken);
        }
        //user가 존재
        else {
            //해당 유저의 oauth provider name이 null이라면 거부, 해당 이메일이 소셜로그인 계정이 아니라고 알림
            if(user.getOauthProviderName() == null) {
                throw new GeneralException(ErrorCode.UOL_EMAIL_IS_NOT_OAUTH_ACCOUNT);
            }
            // 로그인 인증 완료, accessToken 부여하고 로그인 처리 페이지로 redirect
            String accessToken = jwtTokenProvider.createAccessToken(user.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), accessToken);
            redirectURL = getFullSuccessRedirectURL(FRONT_SUCCESS_REDIRECT_URL, accessToken, refreshToken);
        }
        response.sendRedirect(redirectURL);
    }

    private String getFullJoinRedirectURL(String redirectURL, String email, String joinToken) {
        return redirectURL + "?email=" + email + "&joinToken=" + joinToken;
    }

    private String getFullSuccessRedirectURL(String redirectURL, String accessToken, String refreshToken) {
        return redirectURL + "?accessToken=" + accessToken + "&refreshToken=" + refreshToken;
    }
}
