package com.YammyEater.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserJoinRequest(
        @NotBlank
        String code,
        @Email
        @NotBlank
        String email,
        @Length(min = 3)
        @NotBlank
        String username,
        @Length(min = 8)
        @NotBlank
        String password
) {
}
