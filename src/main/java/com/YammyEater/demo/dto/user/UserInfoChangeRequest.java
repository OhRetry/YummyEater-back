package com.YammyEater.demo.dto.user;

import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public record UserInfoChangeRequest(
        String password,
        @Length(min = 3)
        String newUserName,
        @Length(min = 8)
        String newPassword
) {
}
