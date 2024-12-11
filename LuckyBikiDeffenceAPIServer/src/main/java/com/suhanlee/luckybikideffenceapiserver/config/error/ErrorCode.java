package com.suhanlee.luckybikideffenceapiserver.config.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //USER(10000X)
    USER_NOT_FOUND(404, 100001, "USER_NOT_FOUND"),
    PROFILE_NOT_FOUND(404, 100102, "PROFILE_NOT_FOUND"),
    PROFILE_ALREADY_EXIST(404, 100103, "PROFILE_ALREADY_EXIST"),
    //CURRENCY(20000X)
    GOLD_NOT_FOUND(404, 200001, "GOLD_NOT_FOUND"),
    CHANGE_AMOUNT_NOT_ENOUGH(400, 200002, "CHANGE_AMOUNT_NOT_ENOUGH"),
    DUPLICATE_GOLD_IDEMPOTENT_KEY(400, 200003, "DUPLICATE_GOLD_IDEMPOTENT_KEY"),
    Lock_FAILED(400, 200004, "Lock_FAILED"),
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
