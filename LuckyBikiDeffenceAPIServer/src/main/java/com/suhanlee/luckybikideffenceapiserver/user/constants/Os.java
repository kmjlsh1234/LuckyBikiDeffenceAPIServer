package com.suhanlee.luckybikideffenceapiserver.user.constants;

import lombok.Getter;

@Getter
public enum Os {
    ANDROID(0),
    IOS(1),
    ;

    Os(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;
}
