package com.suhanlee.luckybikideffenceapiserver.user.model;

import lombok.Getter;
import lombok.Setter;

//로그인 입력값
@Getter
@Setter
public class LoginViewModel {
    private String email;
    private String password;
}
