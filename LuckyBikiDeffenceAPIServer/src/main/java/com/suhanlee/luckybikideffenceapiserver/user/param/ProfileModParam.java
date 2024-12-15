package com.suhanlee.luckybikideffenceapiserver.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileModParam {
    @JsonIgnore
    private long userId;
    private String nickname;
    private String image;
}
