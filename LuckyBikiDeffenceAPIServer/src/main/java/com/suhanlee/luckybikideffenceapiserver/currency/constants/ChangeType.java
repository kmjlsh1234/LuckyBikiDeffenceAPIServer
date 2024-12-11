package com.suhanlee.luckybikideffenceapiserver.currency.constants;

import lombok.Getter;

@Getter
public enum ChangeType {
    DEPOSIT(0),
    WITHDRAW(1),
    ;

    private final int value;

    ChangeType(int value) {
        this.value = value;
    }
}
