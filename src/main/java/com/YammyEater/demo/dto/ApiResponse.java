package com.YammyEater.demo.dto;

import com.YammyEater.demo.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private final int errorCode;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> of(T data, ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), data);
    }
}
