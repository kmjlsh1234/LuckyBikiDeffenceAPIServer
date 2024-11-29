package com.suhanlee.luckybikideffenceapiserver.user.param;

import lombok.Getter;

@Getter
public class UserJoinParam {
    private String email;
    private String password;
    private String nickname;
}
