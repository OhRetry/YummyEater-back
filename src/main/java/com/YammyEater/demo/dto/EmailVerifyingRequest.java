package com.YammyEater.demo.dto;


import javax.validation.constraints.NotBlank;

public record EmailVerifyingRequest(
        @NotBlank
        String code
) {
}
