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
    WRONG_EMAIL_OR_PASSWORD("UL00000", HttpStatus.OK, "이메일이나 비밀번호를 다시 확인해주세요.");

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