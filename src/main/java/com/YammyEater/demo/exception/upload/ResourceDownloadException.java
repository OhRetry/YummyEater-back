package com.YammyEater.demo.exception.upload;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.exception.GeneralException;

public class ResourceDownloadException extends GeneralException {
    public ResourceDownloadException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ResourceDownloadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
