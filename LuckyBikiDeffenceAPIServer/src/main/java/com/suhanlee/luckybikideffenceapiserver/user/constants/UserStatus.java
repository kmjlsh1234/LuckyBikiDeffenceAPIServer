package com.suhanlee.luckybikideffenceapiserver.user.constants;

import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(0),
    LOGOUT(1),
    EXIT(2),
    BAN(3),
    ;
    final int status;

    UserStatus(int status){
        this.status = status;
    }
}
