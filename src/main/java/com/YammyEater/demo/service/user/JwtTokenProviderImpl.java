package com.YammyEater.demo.service.user;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.domain.user.RefreshTokenInfo;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.user.RefreshTokenInfoRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

/*
 * user의 인증에 관한 jwt token을 발행하는 클래스
 */
@Service
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.access_token_expire_minute}")
    private int accessTokenExpireMinute;

    @Value("${jwt.refresh_token_expire_minute}")
    private int refreshTokenExpireMinute;

    private final RefreshTokenInfoRepository refreshTokenInfoRepository;

    /*
     * access token 생성 후 반환
     */
    @Override
    public String createAccessToken(Long userId) {
        return createToken(String.valueOf(userId), accessTokenExpireMinute);
    }

    /*
     * refresh token 생성 후 userId와 accessToken과 함께 db에 저장
     * 성공 시 refresh token 반환
     */
    @Override
    public String createRefreshToken(Long userId, String accessToken) {
        String refreshToken = createToken(String.valueOf(userId), refreshTokenExpireMinute);
        saveRefreshTokenInfo(refreshToken, accessToken, userId);
        return refreshToken;
    }

    /*
     * refresh token을 이용해 access token을 갱신 후 반환
     * 성공 시 access token 반환
     * 실패 시 null 반환
     */
    @Override
    public String refreshAccessToken(String refreshToken, String accessToken) {
        Long userId = validateRefreshTokenAndGetUserId(
                refreshToken,
                accessToken
        );
        if(userId == null) {
            return null;
        }
        String newAccessToken = createAccessToken(userId);
        saveRefreshTokenInfo(refreshToken, newAccessToken, userId);
        return newAccessToken;
    }

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

    /*
     * refresh token의 jwt 검증, db의 마지막 access token과 검증
     * 검증에 성공, 유효한 userId이면 userId 반환
     * 검증에 실패한다면 null 반환
     */
    @Override
    public Long validateRefreshTokenAndGetUserId(String refreshToken, String lastAccessToken) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (JwtException jwtException) {
            return null;
        }

        Long userId = Long.parseLong(claims.getSubject());

        //refreshToken에 대해 저장된 db 정보와 비교, 검증
        RefreshTokenInfo refreshTokenInfo = refreshTokenInfoRepository.findById(refreshToken).orElse(null);
        if(refreshTokenInfo == null) {
            return null;
        }
        //마지막 accessToken이 같지 않다면 null
        if(!refreshTokenInfo.getLastAccessToken().equals(lastAccessToken)) {
            return null;
        }
        //userId가 같지 않다면 null
        if(refreshTokenInfo.getUserId() != userId) {
            return null;
        }

        return userId;
    }

    private String createToken(String subject, int expireTime) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, expireTime);
        Date expiredAt = calendar.getTime();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(subject)
                .setIssuer("YammyEater")
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .compact();
    }

    private void saveRefreshTokenInfo(String refreshToken, String accessToken, Long userId) {
        RefreshTokenInfo refreshTokenInfo = new RefreshTokenInfo(refreshToken, accessToken, userId);
        refreshTokenInfoRepository.save(refreshTokenInfo);
    }
}
