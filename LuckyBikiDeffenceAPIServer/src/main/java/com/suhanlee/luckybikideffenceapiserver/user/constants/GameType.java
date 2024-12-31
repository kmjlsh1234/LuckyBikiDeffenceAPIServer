package com.suhanlee.luckybikideffenceapiserver.user.constants;

import lombok.Getter;

@Getter
public enum GameType {
    SINGLE(0),
    MULTI(1),
    ;
    private final int value;
    GameType(int value) {
        this.value = value;
    }
}
