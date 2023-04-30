package com.YammyEater.demo.dto.user;

import javax.validation.constraints.NotBlank;

public record RefreshAccessTokenRequest(
        @NotBlank
        String accessToken,
        @NotBlank
        String refreshToken
) {

}
