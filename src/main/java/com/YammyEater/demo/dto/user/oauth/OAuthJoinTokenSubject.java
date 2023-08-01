package com.YammyEater.demo.dto.user.oauth;

import com.YammyEater.demo.constant.user.OAuthProvider;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthJoinTokenSubject {
    private String email;
    private OAuthProvider oAuthProvider;
}
