package com.suhanlee.luckybikideffenceapiserver.currency.constants;

import lombok.Getter;

@Getter
public enum ChargeType {
    CHARGE(0),
    ITEM(1),
    ;
    private final int value;
    ChargeType(int value) {
        this.value = value;
    }
}
