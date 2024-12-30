package com.suhanlee.luckybikideffenceapiserver.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpUtil {

    private static final int EX_SEED = 50;
    private static final int BASE_EX = 100;

    public long getTotalEx(long level){
        return BASE_EX + level * EX_SEED;
    }

}
