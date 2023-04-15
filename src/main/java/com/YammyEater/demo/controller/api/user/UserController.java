package com.YammyEater.demo.controller.api.user;

import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.DuplicateCheckResponse;
import com.YammyEater.demo.dto.user.EmailVerifyingRequest;
import com.YammyEater.demo.dto.user.EmailVerifyingResponse;
import com.YammyEater.demo.dto.user.SendEmailVerifyingRequest;
import com.YammyEater.demo.dto.user.SignInRequest;
import com.YammyEater.demo.dto.user.SignInResponse;
import com.YammyEater.demo.dto.user.UserJoinRequest;
import com.YammyEater.demo.service.user.UserJoinService;
import com.YammyEater.demo.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserJoinService userJoinService;
    private final UserService userService;

    @PostMapping("/api/user/join/sendVerificationEmail")
    public ApiResponse sendVerificationEmail(@RequestBody @Valid SendEmailVerifyingRequest sendEmailVerifyingRequest) {
        userJoinService.sendVerifyingEmail(sendEmailVerifyingRequest.email());
        return ApiResponse.of(null);
    }

    @GetMapping("/api/user/join/isDuplicateEmail")
    public ApiResponse<DuplicateCheckResponse> checkDuplicatedEmail(@RequestParam("email") String email) {
        boolean duplicated = userService.existsByEmail(email);
        return ApiResponse.of(new DuplicateCheckResponse(duplicated));
    }

    @GetMapping("/api/user/join/isDuplicateUserName")
    public ApiResponse<DuplicateCheckResponse> checkDuplicatedUsername(@RequestParam("userName") String username) {
        boolean duplicated = userService.existsByUsername(username);
        return ApiResponse.of(new DuplicateCheckResponse(duplicated));
    }

    @PostMapping("/api/user/join/verifyEmail")
    public ApiResponse<EmailVerifyingResponse> verifyEmail(@RequestBody @Valid EmailVerifyingRequest emailVerifyingRequest) {
        boolean success = userJoinService.verifyEmailByCode(emailVerifyingRequest.code());
        return ApiResponse.of(new EmailVerifyingResponse(success));
    }

    @PostMapping("/api/user/join")
    public ApiResponse<Object> join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        userJoinService.joinByCode(
                userJoinRequest.code(),
                userJoinRequest.email(),
                userJoinRequest.username(),
                userJoinRequest.password()
        );
        return ApiResponse.of(null);
    }

    @PostMapping("/api/user/signIn")
    public ApiResponse<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        String token = userService.authenticate(signInRequest.email(), signInRequest.password());
        return ApiResponse.of(new SignInResponse(token));
    }
}
