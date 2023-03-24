package com.YammyEater.demo.service;

import com.YammyEater.demo.constant.ErrorCode;
import com.YammyEater.demo.domain.User;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //어떤 이메일이 회원가입 되었는지 검사
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //어떤 유저명을 쓰는 회원이 존재하는지 검사
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    //이메일, password로 인증 수행
    //인증 성공 시 user id 반환
    //실패 시 null 반환
    public Long getUserIdByCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            return null;
        }
        if(passwordEncoder.matches(password, user.getPassword()) == false) {
            return null;
        }
        return user.getId();
    }

    //로그인 인증 후 jwt토큰 반환
    //실패 시 예외
    public String authenticate(String email, String password) {
        Long userId = getUserIdByCredentials(email, password);
        if(userId == null) {
            throw new GeneralException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
        return jwtTokenProvider.create(userId);
    }
}
