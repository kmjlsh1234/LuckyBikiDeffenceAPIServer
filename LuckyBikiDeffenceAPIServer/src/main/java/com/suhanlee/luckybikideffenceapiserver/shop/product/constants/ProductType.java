package com.suhanlee.luckybikideffenceapiserver.shop.product.constants;

import lombok.Getter;

@Getter
public enum ProductType {
    CURRENCY(0), //재화
    ITEM(1), //아이템
    NON_CONSUMABLE(2), //비소모성(광고 제거)
    SUBSCRIPTION(3), //구독 모델
    ;
    private final int value;
    ProductType(int value) {
        this.value = value;
    }
}
