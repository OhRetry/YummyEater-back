package com.YammyEater.demo.dto.user;

import com.YammyEater.demo.domain.user.User;
import java.time.format.DateTimeFormatter;


public record UserDto(
        Long id,
        String email,
        String userName,
        String createdAt
) {
    public static UserDto of(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}