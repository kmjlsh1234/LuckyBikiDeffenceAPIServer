package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suhanlee.luckybikideffenceapiserver.game.constants.GameType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
