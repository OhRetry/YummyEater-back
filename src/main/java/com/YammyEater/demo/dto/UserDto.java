package com.YammyEater.demo.dto;

import com.YammyEater.demo.domain.User;
import java.time.format.DateTimeFormatter;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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