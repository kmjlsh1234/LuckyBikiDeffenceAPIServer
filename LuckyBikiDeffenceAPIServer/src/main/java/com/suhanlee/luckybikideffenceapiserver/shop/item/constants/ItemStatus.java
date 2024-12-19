package com.suhanlee.luckybikideffenceapiserver.shop.item.constants;

import lombok.Getter;

@Getter
public enum ItemStatus {
    READY(0),
    SALE(1),
    STOP(2),
    ;
    private final int value;
    ItemStatus(int value) {
        this.value = value;
    }
}
