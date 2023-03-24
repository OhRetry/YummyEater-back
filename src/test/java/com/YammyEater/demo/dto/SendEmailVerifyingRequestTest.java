package com.YammyEater.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class SendEmailVerifyingRequestTest implements BeanValidationSupport {

    static final SendEmailVerifyingRequest trueValue = new SendEmailVerifyingRequest("test@gmail.com");

    @Test
    @DisplayName("검증 성공 테스트")
    void trueValueTest() {
        assertTrue(getValidator().validate(trueValue).isEmpty());
    }

    @Test
    @DisplayName("이메일 null 테스트")
    void email_isNot_null() {
        SendEmailVerifyingRequest targ = new SendEmailVerifyingRequest(null);

        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), NotBlank.class)
        );

    }

    @ParameterizedTest
    @DisplayName("이메일 형식 검증 실패 테스트")
    @ValueSource(strings = {"qqq", "test@", "test@@"})
    void email_has_emailFormat(String email) {
        SendEmailVerifyingRequest targ = new SendEmailVerifyingRequest(email);

        assertTrue(
                checkConstraintViolation(getValidator().validate(targ), Email.class)
        );
    }
}