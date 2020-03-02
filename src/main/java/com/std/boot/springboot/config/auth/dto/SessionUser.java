package com.std.boot.springboot.config.auth.dto;

import com.std.boot.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
인증된 사용자 정보만 필요하므로
name, email, picture만 필드로 선언
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
