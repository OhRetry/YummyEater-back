package com.YammyEater.demo.exception;

import com.YammyEater.demo.constant.error.ErrorCode;

public class ResourceUploadException extends GeneralException{

    public ResourceUploadException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ResourceUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
