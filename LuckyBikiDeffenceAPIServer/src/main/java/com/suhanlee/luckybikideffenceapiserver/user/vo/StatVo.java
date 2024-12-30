package com.suhanlee.luckybikideffenceapiserver.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StatVo {
    private long id;
    private long userId;
    private long longestPlayTime;
    private long bossKillCount;
    private long killCount;
    private long soloPlayTime;
    private long multiPlayTime;
    private int soloPlayCount;
    private int multiPlayCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
