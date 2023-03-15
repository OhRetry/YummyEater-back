package com.YammyEater.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //COMMON
    SUCCESS("C00000",HttpStatus.OK,"SUCCESS"),
    BAD_REQUEST("C10000", HttpStatus.BAD_REQUEST,"BAD REQUEST");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
