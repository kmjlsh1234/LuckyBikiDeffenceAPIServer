package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class StatModParam {
    @JsonIgnore
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
