package com.YammyEater.demo.config;

import com.YammyEater.demo.security.JwtAuthenticationFilter;
import com.YammyEater.demo.service.user.JwtTokenProvider;
import com.YammyEater.demo.service.user.oauth.GoogleOAuthUserServiceImpl;
import com.YammyEater.demo.service.user.oauth.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final GoogleOAuthUserServiceImpl googleOAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //음식 등록
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/food")
                .authenticated();
        //음식 삭제
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/food/**")
                .authenticated();
        //리뷰 등록
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/food/**/review")
                .authenticated();
        //리뷰 수정
        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, "/api/food/review/**")
                .authenticated();
        //리뷰 삭제
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/food/review/**")
                .authenticated();
        //유저 정보 확인
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/user/info")
                .authenticated();
        //유저 정보 수정
        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, "/api/user/info")
                .authenticated();
        //그 외 허용
        http.authorizeRequests()
                .anyRequest()
                .permitAll();

        //jwt 인증 예외에 대해서 응답 로직 등록
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        http.oauth2Login().userInfoEndpoint().userService(googleOAuthUserService).and().successHandler(oAuthSuccessHandler);
    }
}
