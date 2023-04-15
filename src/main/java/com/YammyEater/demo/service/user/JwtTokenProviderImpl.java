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

    @Override
    public String create(Long userId) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, accessTokenExpireMinute);
        Date expiredAt = calendar.getTime();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(String.valueOf(userId))
                .setIssuer("YammyEater")
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .compact();
    }

    @Override
    public Long validateAndGetUserId(String token) {

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
