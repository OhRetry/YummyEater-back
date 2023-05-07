package com.YammyEater.demo.service.user;

import com.YammyEater.demo.dto.user.UserDto;
import com.YammyEater.demo.dto.user.UserInfoChangeRequest;

public interface UserService {
    //어떤 이메일이 회원가입 되었는지 검사
    boolean existsByEmail(String email);

    //어떤 유저명을 쓰는 회원이 존재하는지 검사
    boolean existsByUsername(String username);

    //이메일, password로 인증 수행
    //인증 성공 시 user id 반환
    //실패 시 null 반환
    Long getUserIdByCredentials(String email, String password);

    //로그인 인증 후 user id 반환
    //실패 시 예외
    Long authenticate(String email, String password);

    boolean existsById(Long userId);

    boolean isVaildUser(Long userId);

    UserDto getUserInfo(Long userId);
    void serUserInfo(Long userId, UserInfoChangeRequest userInfoChangeRequest);
}
