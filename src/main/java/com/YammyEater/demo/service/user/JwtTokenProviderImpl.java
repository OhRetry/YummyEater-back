package com.YammyEater.demo.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.access_token_expire_minute}")
    private int accessTokenExpireMinute;

    /*
     * access token 생성 후 반환
     */
    @Override
    public String createAccessToken(Long userId) {
        return createToken(String.valueOf(userId), accessTokenExpireMinute);
    }

    }

    @Override
    /*
     * access token의 jwt 검증
     * 성공 시 userId 반환
     * 실패 시 null 반환
     */
    @Override
    public Long validateAccessTokenAndGetUserId(String token) {

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException jwtException) {
            return null;
        }

        return Long.parseLong(claims.getSubject());
    }
}
