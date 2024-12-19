package com.suhanlee.luckybikideffenceapiserver.currency.constants;

import lombok.Getter;

@Getter
public enum CurrencyType {
    GOLD(0),
    ;
    private final int value;

    CurrencyType(int value) {
        this.value = value;
    }
}
