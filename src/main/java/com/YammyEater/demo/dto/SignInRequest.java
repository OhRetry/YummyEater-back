package com.YammyEater.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SignInRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
