package com.YammyEater.demo.exception;

import com.YammyEater.demo.constant.error.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public GeneralException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }
}

