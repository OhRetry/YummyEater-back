package com.YammyEater.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.YammyEater.demo.dto.user.EmailVerifyingRequest;
import javax.validation.constraints.NotBlank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailVerifyingRequestTest implements BeanValidationSupport {
    static final EmailVerifyingRequest trueValue = new EmailVerifyingRequest("afWer3T4");

    @Test
    @DisplayName("검증 성공 테스트")
    void trueValueTest() {
        assertTrue(getValidator().validate(trueValue).isEmpty());
    }

    @Test
    @DisplayName("인증코드 null 테스트")
    public void code_is_NotNull() {
        EmailVerifyingRequest targ = new EmailVerifyingRequest(null);
        assertTrue(checkConstraintViolation(getValidator().validate(targ), NotBlank.class));
    }
}