package com.suhanlee.luckybikideffenceapiserver.currency.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.ChargeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyAddParam {
    @JsonIgnore
    private long userId;
    private int amount;
    private ChargeType chargeType;
}
