package com.YammyEater.demo.domain.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailVerification implements Serializable {
    private String email;
    private boolean isVerified;
}
