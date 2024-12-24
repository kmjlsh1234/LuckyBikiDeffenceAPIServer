package com.suhanlee.luckybikideffenceapiserver.shop.product.constants;

import lombok.Getter;

@Getter
public enum ProductStatus {
    READY(0),
    SALE(1),
    REMOVE(2),
    ;
    private int value;
    ProductStatus(int value) {
        this.value = value;
    }
}
