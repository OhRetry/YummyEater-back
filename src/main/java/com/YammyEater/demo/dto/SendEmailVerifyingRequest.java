package com.YammyEater.demo.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record SendEmailVerifyingRequest (
        @Email
        @NotBlank
        String email
) {
}
