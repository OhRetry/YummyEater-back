package com.YammyEater.demo.controller.api.user;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.DuplicateCheckResponse;
import com.YammyEater.demo.dto.user.*;
import com.YammyEater.demo.exception.GeneralException;
import com.YammyEater.demo.service.user.JwtTokenProvider;
import com.YammyEater.demo.service.user.UserJoinService;
import com.YammyEater.demo.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserJoinService userJoinService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

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
        Long userId = userService.authenticate(signInRequest.email(), signInRequest.password());
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, accessToken);
        return ApiResponse.of(new SignInResponse(accessToken, refreshToken));
    }

    @PostMapping("/api/user/refreshAccessToken")
    public ApiResponse<RefreshAccessTokenResponse> refreshAccessToken(
            @RequestBody @Valid RefreshAccessTokenRequest refreshAccessTokenRequest
    ) {

            String newAccessToken = jwtTokenProvider.refreshAccessToken(
                    refreshAccessTokenRequest.refreshToken(),
                    refreshAccessTokenRequest.accessToken()
                    );
            if(newAccessToken == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST);
            }
            return ApiResponse.of(new RefreshAccessTokenResponse(newAccessToken));
    }

    @GetMapping("/api/user/info")
    public ApiResponse<UserDto> getUserInfo(@AuthenticationPrincipal Long userId) {
        return ApiResponse.of(userService.getUserInfo(userId));
    }

    @PatchMapping("/api/user/info")
    public ApiResponse<Object> setUserInfo(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid UserInfoChangeRequest userInfoChangeRequest
    ) {
        userService.serUserInfo(userId, userInfoChangeRequest);
        return ApiResponse.of(null);
    }

    @PostMapping("/api/user/oauth/join")
    public ApiResponse<SignInResponse> joinOAuth(@RequestBody @Valid OAuthUserJoinRequest oAuthUserJoinRequest) {
        Long userId = userJoinService.joinByOAuth(oAuthUserJoinRequest);
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, accessToken);
        return ApiResponse.of(new SignInResponse(accessToken, refreshToken));
    }

    @PostMapping("/api/user/resetPassword")
    public ApiResponse<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String newPassword = userService.resetPassword(resetPasswordRequest.email());
        userService.sendResetPasswordEmail(resetPasswordRequest.email(), newPassword);
        return ApiResponse.of(null);
    }
}
