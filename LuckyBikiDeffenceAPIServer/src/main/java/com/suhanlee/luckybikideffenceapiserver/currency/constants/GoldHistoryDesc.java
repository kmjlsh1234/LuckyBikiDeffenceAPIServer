package com.suhanlee.luckybikideffenceapiserver.currency.constants;

import lombok.Getter;

@Getter
public enum GoldHistoryDesc {
    DEPOSIT_BY_WIN_GAME("deposit by win game");

    private String desc;
    GoldHistoryDesc(String desc) {
        this.desc = desc;
    }
}
