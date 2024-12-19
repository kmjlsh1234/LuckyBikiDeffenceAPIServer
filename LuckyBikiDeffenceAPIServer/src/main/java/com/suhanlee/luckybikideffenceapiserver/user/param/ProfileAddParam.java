package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileAddParam {
    @JsonIgnore
    private long userId;
    private int level;
    private String nickname;
}
