package com.YammyEater.demo.config;

import com.YammyEater.demo.security.JwtAuthenticationFilter;
import com.YammyEater.demo.service.user.JwtTokenProvider;
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
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

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
        //그 외 허용
        http.authorizeRequests()
                .anyRequest()
                .permitAll();


        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

    }

}
