package com.suhanlee.luckybikideffenceapiserver.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSimple {
    private long userId;
    private String email;
}
