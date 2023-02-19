package com.YammyEater.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    SUCCESS(0,HttpStatus.OK,"SUCCESS");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
