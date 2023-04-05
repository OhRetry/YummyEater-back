package com.YammyEater.demo.controller.api;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.dto.ApiResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<ApiResponse> error(HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = ErrorCode.valueOf(httpStatus);

        if (httpStatus == HttpStatus.OK) {
            httpStatus = HttpStatus.FORBIDDEN;
            errorCode = ErrorCode.BAD_REQUEST;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponse.of(null, errorCode));
    }
}
