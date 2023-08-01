package com.YammyEater.demo.constant.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OAuthProvider {
    NOT_USE(null), GOOGLE("google");
    private final String name;
}
