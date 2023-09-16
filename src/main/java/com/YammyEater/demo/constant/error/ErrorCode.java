package com.YammyEater.demo.constant.error;

import com.YammyEater.demo.exception.GeneralException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //COMMON
    SUCCESS("C00000",HttpStatus.OK,"SUCCESS"),
    BAD_REQUEST("C10000", HttpStatus.BAD_REQUEST,"BAD REQUEST"),
    UNAUTHORIZED("C10001", HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    FORBIDDEN("C10002", HttpStatus.FORBIDDEN, "FORBIDDEN"),
    NOT_FOUND("C10003", HttpStatus.NOT_FOUND, "PAGE NOT FOUND"),
    INTERNAL_SERVER_ERROR("C20000", HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),

    //User Login
    WRONG_EMAIL_OR_PASSWORD("UL00000", HttpStatus.OK, "이메일이나 비밀번호를 다시 확인해주세요."),

    //User OAuth Login
    UOL_EMAIL_IS_NOT_OAUTH_ACCOUNT("UOL00000", HttpStatus.OK, "해당 이메일은 소셜 로그인 계정이 아닙니다."),

    //User Modify
    UM_WRONG_PASSWORD("UM00000", HttpStatus.OK, "비밀번호가 틀립니다."),
    UM_EMAIL_NOT_EXIST("UM00001",  HttpStatus.OK, "존재하지 않는 이메일입니다."),
    UM_PW_RESET_EMAIL_IS_OAUTH("UM00002", HttpStatus.OK, "OAuth로 가입된 이메일은 비밀번호 재설정이 불가능합니다."),

    //User OAuth Join
    UOJ_JOINTOKEN_EXPIRED("UOJ00000", HttpStatus.OK, "만료된 회원가입 토큰입니다."),
    UOJ_DUPLICATE_USERNAME("UOJ00001", HttpStatus.OK, "중복된 닉네임입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) { throw new GeneralException(INTERNAL_SERVER_ERROR); }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) { return ErrorCode.BAD_REQUEST; }
                    else if (httpStatus.is5xxServerError()) { return ErrorCode.INTERNAL_SERVER_ERROR; }
                    else { return ErrorCode.SUCCESS; }
                });
    }
}
