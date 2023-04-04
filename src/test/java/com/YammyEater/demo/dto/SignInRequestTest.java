package com.YammyEater.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.YammyEater.demo.dto.user.SignInRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SignInRequestTest implements BeanValidationSupport {

    static final SignInRequest trueValue = new SignInRequest("test@gmail.com","1111");

    @Test
    @DisplayName("검증 성공 테스트")
    void trueValueTest() {
        assertTrue(getValidator().validate(trueValue).isEmpty());
    }

    @Test
    @DisplayName("이메일 null 테스트")
    void email_isNot_null() {
        SignInRequest targ = new SignInRequest(null, trueValue.password());

        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), NotBlank.class)
        );

    }

    @ParameterizedTest
    @DisplayName("이메일 형식 검증 실패 테스트")
    @ValueSource(strings = {"qqq", "test@", "test@@"})
    void email_has_emailFormat(String email) {
        SignInRequest targ = new SignInRequest(email, trueValue.password());

        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), Email.class)
        );
    }

    @Test
    @DisplayName("비밀번호 null 테스트")
    void password_isNot_null() {
        SignInRequest targ = new SignInRequest(trueValue.email(), null);
        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), NotBlank.class)
        );
    }


}