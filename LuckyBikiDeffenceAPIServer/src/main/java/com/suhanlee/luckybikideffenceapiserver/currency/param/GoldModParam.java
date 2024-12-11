package com.suhanlee.luckybikideffenceapiserver.currency.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.GoldHistoryDesc;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldModParam {
    @JsonIgnore
    private long userId;
    private int changeAmount;
    private GoldHistoryDesc goldHistoryDesc;
    private String idempotentKey;
}
