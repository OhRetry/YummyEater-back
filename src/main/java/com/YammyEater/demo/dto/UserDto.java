package com.YammyEater.demo.dto;

import com.YammyEater.demo.domain.User;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public record UserDto(Long id, String email, String userName) {
    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getUsername());
    }
}