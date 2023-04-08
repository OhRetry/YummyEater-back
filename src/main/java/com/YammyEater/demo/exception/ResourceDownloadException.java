package com.YammyEater.demo.exception;

import com.YammyEater.demo.constant.error.ErrorCode;

public class ResourceDownloadException extends GeneralException {
    public ResourceDownloadException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ResourceDownloadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
