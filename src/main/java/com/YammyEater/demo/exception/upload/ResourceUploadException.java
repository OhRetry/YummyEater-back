package com.YammyEater.demo.exception.upload;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.exception.GeneralException;

public class ResourceUploadException extends GeneralException {

    public ResourceUploadException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ResourceUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
