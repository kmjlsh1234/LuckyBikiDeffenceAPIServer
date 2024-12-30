package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelAddParam {
    @JsonIgnore
    private long userId;
    private int addLevel;
}
