package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suhanlee.luckybikideffenceapiserver.user.constants.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatUpdateParam {
    @JsonIgnore
    private long userId;
    private GameType gameType;
    private long playTime;
    private int waveCount;
    private long bossKillCount;
    private long killCount;
}
