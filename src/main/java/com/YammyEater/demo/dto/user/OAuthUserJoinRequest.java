package com.YammyEater.demo.dto.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record OAuthUserJoinRequest (
    @NotBlank
    String joinToken,
    @Length(min = 3)
    @NotBlank
    String username
) {
}
