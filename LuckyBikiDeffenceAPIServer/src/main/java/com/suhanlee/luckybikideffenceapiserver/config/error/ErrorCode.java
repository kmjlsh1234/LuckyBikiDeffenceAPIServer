package com.suhanlee.luckybikideffenceapiserver.config.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //USER
    USER_NOT_FOUND(404, 100001, "USER_NOT_FOUND"),

    //CURRENCY(20000X)
    GOLD_NOT_FOUND(404, 200001, "GOLD_NOT_FOUND"),
    CHANGE_AMOUNT_NOT_ENOUGH(400, 200002, "CHANGE_AMOUNT_NOT_ENOUGH"),
    ;

    private final int code;
    private final int status;
    private final String message;

    ErrorCode(final int code, final int status, final String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
