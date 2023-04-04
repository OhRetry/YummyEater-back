package com.YammyEater.demo.dto.user;


import javax.validation.constraints.NotBlank;

public record EmailVerifyingRequest(
        @NotBlank
        String code
) {
}
