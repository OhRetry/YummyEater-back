package com.YammyEater.demo.service.user;

import com.YammyEater.demo.Util.RandomUtil;
import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.constant.user.OAuthProvider;
import com.YammyEater.demo.domain.user.User;
import com.YammyEater.demo.dto.user.UserDto;
import com.YammyEater.demo.dto.user.UserInfoChangeRequest;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.repository.user.UserRepository;
import com.YammyEater.demo.service.mail.MailService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RandomUtil randomUtil;
    private final MailService mailService;

    private final String resetPasswordEmailTitle = "YummyEater 비밀번호가 재설정되었습니다.";
    private String RESETPASSWORD_EMAIL_BODY;
    private final String RESETPASSWORD_EMAIL_NEWPW_REPLACE_STRING = "@_NEWPW_@";

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource resource = new ClassPathResource("mail/mail-newpw.html");
        Path path = Paths.get(resource.getURI());
        RESETPASSWORD_EMAIL_BODY = Files.readString(path);
    }

    //어떤 이메일이 회원가입 되었는지 검사
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //어떤 유저명을 쓰는 회원이 존재하는지 검사
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean isVaildUser(Long userId) {
        return existsById(userId);
    }

    //이메일, password로 인증 수행
    //인증 성공 시 user id 반환
    //실패 시 null 반환
    @Override
    public Long getUserIdByCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            return null;
        }
        if(user.getOauthProviderName() != null) {
            return null;
        }
        if(passwordEncoder.matches(password, user.getPassword()) == false) {
            return null;
        }
        return user.getId();
    }

    //로그인 인증 후 access token과 refresh token 반환
    //실패 시 예외
    @Override
    public Long authenticate(String email, String password) {
        Long userId = getUserIdByCredentials(email, password);
        if(userId == null) {
            throw new GeneralException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
        return userId;

    }

    @Override
    public UserDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));
        return UserDto.of(user);
    }

    @Override
    @Transactional
    public void serUserInfo(Long userId, UserInfoChangeRequest userInfoChangeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));
        //비밀번호가 다름
        if(!passwordEncoder.matches(userInfoChangeRequest.password(), user.getPassword())){
            throw new GeneralException(ErrorCode.UM_WRONG_PASSWORD);
        }
        if(userInfoChangeRequest.newUserName() != null) {
            user.setUsername(userInfoChangeRequest.newUserName());
        }
        if(userInfoChangeRequest.newPassword() != null) {
            user.setPassword(passwordEncoder.encode(userInfoChangeRequest.newPassword()));
        }
    }

    @Override
    @Transactional
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new GeneralException(ErrorCode.UM_EMAIL_NOT_EXIST);
        }
        if(user.getOauthProviderName() != OAuthProvider.NOT_USE.getName()) {
            throw new GeneralException(ErrorCode.UM_PW_RESET_EMAIL_IS_OAUTH);
        }
        String newPassword = randomUtil.getRandomString(8);
        user.setPassword(passwordEncoder.encode(newPassword));

        return newPassword;
    }

    @Override
    public void sendResetPasswordEmail(String email, String newPassword) {
        String mailBody = RESETPASSWORD_EMAIL_BODY.replace(RESETPASSWORD_EMAIL_NEWPW_REPLACE_STRING, newPassword);
        mailService.sendEmail(email, resetPasswordEmailTitle, mailBody);
    }
}
