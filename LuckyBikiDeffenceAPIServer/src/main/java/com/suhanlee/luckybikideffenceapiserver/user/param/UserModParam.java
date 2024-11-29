package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import lombok.Getter;

@Getter
public class UserModParam {
    private String email;
    private String password;
    private String nickname;
    private UserStatus status;
}
