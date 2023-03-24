package com.YammyEater.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserJoinRequestTest implements BeanValidationSupport {

    static final UserJoinRequest trueValue = new UserJoinRequest("1112", "test@gmail.com","user1","qwertyui123!");

    @Test
    @DisplayName("검증 성공 테스트")
    void trueValueTest() {
        assertTrue(getValidator().validate(trueValue).isEmpty());
    }

    @Test
    @DisplayName("인증코드 null 테스트")
    public void code_is_NotNull() {
        UserJoinRequest targ = new UserJoinRequest(null, trueValue.email(), trueValue.username(), trueValue.password());
        assertTrue(checkConstraintViolation(getValidator().validate(targ), NotBlank.class));
    }


    @Test
    @DisplayName("이메일 null 테스트")
    void email_isNot_null() {
        UserJoinRequest targ = new UserJoinRequest(trueValue.code(), null, trueValue.username(), trueValue.password());

        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), NotBlank.class)
        );

    }

    @ParameterizedTest
    @DisplayName("이메일 형식 검증 실패 테스트")
    @ValueSource(strings = {"qqq", "test@", "test@@"})
    void email_has_emailFormat(String email) {
        UserJoinRequest targ = new UserJoinRequest(trueValue.code(), email, trueValue.username(), trueValue.password());

        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), Email.class)
        );
    }



    @Test
    @DisplayName("유저명 null 테스트")
    void username_isNot_null() {
        UserJoinRequest targ = new UserJoinRequest(trueValue.code(), trueValue.email(), null, trueValue.password());
        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), NotBlank.class)
        );
    }

    @ParameterizedTest
    @DisplayName("유저명 길이제한 테스트")
    @ValueSource(strings = {"q", "qw"})
    void username_length_greater_than_2(String username) {
        UserJoinRequest targ = new UserJoinRequest(trueValue.code(), trueValue.email(), username, trueValue.password());
        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), Length.class)
        );
    }


    @Test
    @DisplayName("비밀번호 null 테스트")
    void password_isNot_null() {
        UserJoinRequest targ = new UserJoinRequest(trueValue.code(), trueValue.email(), trueValue.username(), null);
        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), NotBlank.class)
        );
    }

    @ParameterizedTest
    @DisplayName("비밀번호 길이제한 테스트")
    @ValueSource(strings = {"111", "qwertyu"})
    void password_length_greater_than_7(String password) {
        UserJoinRequest targ = new UserJoinRequest(trueValue.code(), trueValue.email(), trueValue.username(), password);
        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), Length.class)
        );
    }
}